package org.jakartaee5g23.sportsfieldbooking.services.impls;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.events.HandleFileEvent;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.file.ChunkUploadProgress;
import org.jakartaee5g23.sportsfieldbooking.enums.HandleFileAction;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileException;
import org.jakartaee5g23.sportsfieldbooking.services.BaseRedisService;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.concurrent.TimeUnit.DAYS;
import static org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode.*;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.*;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.generateFileName;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.isMedia;
import static org.springframework.http.HttpStatus.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MinioClientServiceImpl implements MinioClientService {

    MinioClient minioClient;

    KafkaTemplate<String, HandleFileEvent> fileStorageTemplate;

    BaseRedisService<String, String, ChunkUploadProgress> baseRedisService;

    ConcurrentHashMap<String, List<ChunkUploadProgress>> uploadStatusMap = new ConcurrentHashMap<>();

    @Value("${minio.bucket-name}")
    @NonFinal
    String bucketName;

    @Value("${minio.temp-bucket-name}")
    @NonFinal
    String tempBucketName;

    public MinioClientServiceImpl(@Value("${minio.endpoint}") String endpoint,
                                  @Value("${minio.access-key}") String accessKey,
                                  @Value("${minio.secret-key}") String secretKey,
                                  @Autowired KafkaTemplate<String, HandleFileEvent> fileStorageTemplate,
                                  @Autowired BaseRedisService<String, String, ChunkUploadProgress> baseRedisService) {
        this.minioClient = MinioClient.builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build();

        this.fileStorageTemplate = fileStorageTemplate;
        this.baseRedisService = baseRedisService;
    }

    @PostConstruct
    public void initDefaultUrls() {
        DEFAULT_AVATAR_URL = getObjectUrl("user-info.png", bucketName);
    }

//    @PostConstruct
//    public void setBucketLifecyclePolicy() {
//        try {
//            ensureBucketExists(tempBucketName);
//            // Tạo policy xóa file sau 1 ngày
//            // Cấu hình Expiration sau 1 ngày
//            Expiration expiration = new Expiration(ZonedDateTime.now(), 1, true);  // days = 1 để chỉ định xóa sau 1 ngày
//
//            // Thiết lập Filter để áp dụng cho tất cả các object trong bucket
//            RuleFilter filter = new RuleFilter(null, "", null);  // Không cần chỉ định prefix, áp dụng cho toàn bộ bucket
//
//            LifecycleRule rule = new LifecycleRule(
//                    Status.ENABLED,                   // Kích hoạt Rule
//                    null,                             // Không cần AbortIncompleteMultipartUpload
//                    expiration,                       // Cấu hình Expiration với 1 ngày
//                    filter,                           // Áp dụng cho tất cả các object
//                    "expire-rule",                    // ID cho Rule
//                    null,                             // Không cần NoncurrentVersionExpiration
//                    null,                             // Không cần NoncurrentVersionTransition
//                    null                              // Không cần Transition
//            );
//
//            LifecycleConfiguration config = new LifecycleConfiguration(Collections.singletonList(rule));
//
//            // Áp dụng Lifecycle Configuration vào bucket
//            minioClient.setBucketLifecycle(
//                    SetBucketLifecycleArgs.builder()
//                            .bucket(bucketName)
//                            .config(config)
//                            .build()
//            );
//
//            log.info("Bucket lifecycle policy set to expire files after 1 day.");
//
//        } catch (Exception e) {
//            log.error("Error setting lifecycle policy: {}", e.getMessage());
//            throw new FileException(CAN_NOT_CHECK_BUCKET, BAD_REQUEST);
//        }
//    }

    @Override
    public long uploadChunk(MultipartFile file, String fileMetadataId, String chunkHash, long startByte, long totalSize, String contentType) {
        if (file.getSize() > MAX_CHUNK_SIZE) throw new FileException(FILE_TOO_LARGE, BAD_REQUEST);

        if (totalSize > MAX_FILE_SIZE) throw new FileException(FILE_TOO_LARGE, BAD_REQUEST);

        if (!isMedia(contentType)) throw new FileException(INVALID_FILE_TYPE, BAD_REQUEST);

        //if (hashFileChunk(file).equals(chunkHash)) throw new FileException(INVALID_FILE_PROVIDED, BAD_REQUEST);

        uploadStatusMap.putIfAbsent(fileMetadataId, new CopyOnWriteArrayList<>());
        List<ChunkUploadProgress> uploadStatus = uploadStatusMap.get(fileMetadataId);

        // Nếu chunk đã upload trước đó thì bỏ qua
        if (uploadStatus.stream().anyMatch(i -> i.getChunkPosition() == startByte && i.isUploaded()))
            throw new FileException(CHUNK_ALREADY_EXISTS, OK);

        // Tạo thư mục tạm nếu chưa có
        Path tempDir = Paths.get(TEMP_DIR);
        if (!Files.exists(tempDir)) {
            try {
                Files.createDirectories(tempDir);
            } catch (IOException e) {
                log.error("Error creating temp directory", e);
                throw new FileException(FileErrorCode.CAN_NOT_INIT_BACKUP_FOLDER, BAD_REQUEST);
            }
        }

        // Lưu chunk vào thư mục tạm
        File chunkFile = new File(TEMP_DIR + fileMetadataId + "_" + startByte);
        try (FileOutputStream fos = new FileOutputStream(chunkFile)) {
            fos.write(file.getBytes());

        } catch (IOException e) {
            log.error("Error writing chunk to disk", e);
            throw new FileException(FileErrorCode.COULD_NOT_WRITE_CHUNK, BAD_REQUEST);
        }

        // Lưu trạng thái upload
        saveChunkProgress(startByte, uploadStatus);

        // Kiểm tra nếu đã nhận đủ các chunk, kết hợp lại
        if (startByte + file.getSize() == totalSize) {
            try {
                combineChunks(fileMetadataId, totalSize);
            } catch (Exception e) {
                log.error("Error combining chunks", e);
                throw new FileException(FileErrorCode.COULD_NOT_COMBINE_CHUNKS, BAD_REQUEST);
            }

            File fileAfterCombine = new File(TEMP_DIR + fileMetadataId);
            String newFileName = generateFileName(contentType.split("/")[0], contentType.split("/")[1]);
            try {
                storeObject(fileAfterCombine, newFileName, contentType, bucketName);
                Files.delete(fileAfterCombine.toPath()); // Xóa file sau khi upload hoàn thành
            } catch (Exception e) {
                log.error("Error storing file", e);
                throw new FileException(FileErrorCode.CAN_NOT_STORE_FILE, BAD_REQUEST);
            }

            fileStorageTemplate.send(KAFKA_TOPIC_HANDLE_FILE, HandleFileEvent.builder()
                .objectKey(newFileName)
                .size(fileAfterCombine.length())
                .contentType(contentType)
                .url(getObjectUrl(newFileName, bucketName))
                .action(HandleFileAction.UPLOAD)
                .build());

            // Xóa trạng thái sau khi upload hoàn thành
            uploadStatusMap.remove(fileMetadataId);
            return totalSize;
        }

        return startByte + file.getSize();
    }

    @Override
    public void storeObject(File file, String fileName, String contentType, String bucketName) {
        try {
            ensureBucketExists(bucketName);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(Files.newInputStream(file.toPath()), file.length(), -1)
                    .contentType(contentType)
                    .build());

        } catch (Exception e) {
            throw new FileException(CAN_NOT_STORE_FILE, BAD_REQUEST);
        }
    }

    @Override
    public String getObjectUrl(String objectKey, String bucketName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectKey)
                    .expiry(1, DAYS) // 1 week
                    .build());

        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new FileException(COULD_NOT_READ_FILE, BAD_REQUEST);
        }
    }

    @Override
    public void deleteObject(String objectKey, String bucketName) {
        GetObjectResponse response;
        try {
            response = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectKey)
                    .build());
        } catch (Exception e) {
            throw new FileException(COULD_NOT_READ_FILE, BAD_REQUEST);
        }

        if (response == null) throw new FileException(FILE_NOT_FOUND, BAD_REQUEST);

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectKey)
                .build());

        } catch (Exception e) {
            throw new FileException(CAN_NOT_DELETE_FILE, BAD_REQUEST);
        }
    }

    private void ensureBucketExists(String bucketName) {
        boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket '{}' created.", bucketName);
            }
        } catch (Exception e) {
            throw new FileException(CAN_NOT_CHECK_BUCKET, BAD_REQUEST);
        }
    }

    private void combineChunks(String fileMetadataId, long totalSize) throws Exception {
        // File đầu ra sau khi đã ghép các chunk
        File outputFile = new File(TEMP_DIR + fileMetadataId);

        // Kiểm tra nếu file đầu ra đã tồn tại trước đó, thì xóa đi để bắt đầu lại
        if (outputFile.exists()) Files.delete(outputFile.toPath());

        try (FileOutputStream fos = new FileOutputStream(outputFile, true)) {
            long bytesWritten = 0;

            // Duyệt qua từng chunk dựa trên startByte để ghép vào file
            while (bytesWritten < totalSize) {
                Path chunkPath = Paths.get(TEMP_DIR + fileMetadataId + "_" + bytesWritten);

                if (!Files.exists(chunkPath))
                    throw new FileException(FileErrorCode.CHUNK_MISSING, BAD_REQUEST);

                byte[] chunkBytes = Files.readAllBytes(chunkPath);
                fos.write(chunkBytes);

                // Cập nhật số byte đã ghi
                bytesWritten += chunkBytes.length;

                // Xóa chunk sau khi ghép vào file
                Files.delete(chunkPath);
            }
        } catch (IOException e) {
            throw new FileException(FileErrorCode.COULD_NOT_COMBINE_CHUNKS, BAD_REQUEST);
        }
    }

    // Hash lại chunk (sử dụng SHA-256)
    private String hashFileChunk(MultipartFile fileChunk) {
        byte[] hashBytes;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] chunkBytes = fileChunk.getBytes();
            hashBytes = digest.digest(chunkBytes);

        } catch (Exception e) {
            throw new FileException(INVALID_FILE_PROVIDED, BAD_REQUEST);
        }
        return bytesToHex(hashBytes);
    }

    // Lưu tiến độ chunk
    private void saveChunkProgress(long startByte, List<ChunkUploadProgress> uploadStatus) {
        // Add or update the chunk upload status
        synchronized (uploadStatus) {
            // Find existing chunk progress for the given position, or create a new one
            ChunkUploadProgress chunkProgress = uploadStatus.stream()
                    .filter(i -> i.getChunkPosition() == startByte)
                    .findFirst()
                    .orElseGet(() -> {
                        ChunkUploadProgress newChunk = new ChunkUploadProgress(startByte, false);
                        uploadStatus.add(newChunk);
                        return newChunk;
                    });

            // Mark the chunk as uploaded
            chunkProgress.setUploaded(true);
        }
    }

    // Chuyển đổi mảng byte thành chuỗi hex (để so sánh với hash từ client)
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
