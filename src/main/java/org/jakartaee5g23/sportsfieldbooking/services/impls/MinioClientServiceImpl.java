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
import org.jakartaee5g23.sportsfieldbooking.enums.HandleFileAction;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileException;
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
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;
import static org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode.*;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.*;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.generateFileName;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.isMedia;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MinioClientServiceImpl implements MinioClientService {

    MinioClient minioClient;

    KafkaTemplate<String, HandleFileEvent> fileStorageTemplate;

    ConcurrentHashMap<String, boolean[]> uploadStatusMap = new ConcurrentHashMap<>();

    @Value("${minio.bucket-name}")
    @NonFinal
    String bucketName;

    public MinioClientServiceImpl(@Value("${minio.endpoint}") String endpoint,
                                  @Value("${minio.access-key}") String accessKey,
                                  @Value("${minio.secret-key}") String secretKey,
                                  @Autowired KafkaTemplate<String, HandleFileEvent> fileStorageTemplate) {
        this.minioClient = MinioClient.builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build();

        this.fileStorageTemplate = fileStorageTemplate;
    }

    @PostConstruct
    public void initDefaultUrls() {
        DEFAULT_AVATAR_URL = getObjectUrl("user-info.png");
    }

    @Override
    public String uploadChunk(MultipartFile file, String fileName, int chunkNumber, int totalChunks, String contentType) {
        if (file.getSize() > 10 * 1024 * 1024) throw new FileException(FILE_TOO_LARGE, BAD_REQUEST);

        if (!isMedia(contentType)) throw new FileException(INVALID_FILE_TYPE, BAD_REQUEST);

        // Lưu trạng thái upload
        uploadStatusMap.putIfAbsent(fileName, new boolean[totalChunks]);
        boolean[] uploadStatus = uploadStatusMap.get(fileName);

        // Nếu chunk đã upload trước đó thì bỏ qua
        if (uploadStatus[chunkNumber]) throw new FileException(CHUNK_ALREADY_EXISTS, OK);

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
        File chunkFile = new File(TEMP_DIR + fileName + "_" + chunkNumber);
        try (FileOutputStream fos = new FileOutputStream(chunkFile)) {
            fos.write(file.getBytes());

        } catch (IOException e) {
            log.error("Error writing chunk to disk", e);
            throw new FileException(FileErrorCode.COULD_NOT_WRITE_CHUNK, BAD_REQUEST);
        }

        // Đánh dấu chunk này đã được upload
        uploadStatus[chunkNumber] = true;

        // Kiểm tra nếu đã nhận đủ các chunk, kết hợp lại
        if (isUploadComplete(uploadStatus)) {
            try {
                combineChunks(fileName, totalChunks);
            } catch (Exception e) {
                log.error("Error combining chunks", e);
                throw new FileException(FileErrorCode.COULD_NOT_COMBINE_CHUNKS, BAD_REQUEST);
            }

            File fileAfterCombine = new File(TEMP_DIR + fileName);
            //String newFileName = generateFileName(contentType.split("/")[0], contentType.split("/")[1]);
            try {
                storeObject(fileAfterCombine, fileName, contentType);
                Files.delete(fileAfterCombine.toPath()); // Xóa file sau khi upload hoàn thành
            } catch (Exception e) {
                log.error("Error storing file", e);
                throw new FileException(FileErrorCode.CAN_NOT_STORE_FILE, BAD_REQUEST);
            }
            // Xóa trạng thái sau khi upload hoàn thành
            uploadStatusMap.remove(fileName);

            fileStorageTemplate.send(KAFKA_TOPIC_HANDLE_FILE, HandleFileEvent.builder()
                .objectKey(fileName)
                .size(fileAfterCombine.length())
                .contentType(contentType)
                .url(getObjectUrl(fileName))
                .action(HandleFileAction.UPLOAD)
                .build());

            return "file_upload_success";
        }

        return "chunk_uploaded";
    }

    @Override
    public void storeObject(File file, String fileName, String contentType) {
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
    public String getObjectUrl(String objectKey) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectKey)
                    .build());

        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new FileException(COULD_NOT_READ_FILE, BAD_REQUEST);
        }
    }

    @Override
    public void deleteObject(String objectKey) {
        GetObjectResponse response = null;
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

    private void ensureBucketExists(String bucketName) throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("Bucket '{}' created.", bucketName);
        }
    }

    private boolean isUploadComplete(boolean[] uploadStatus) {
        for (boolean status : uploadStatus) {
            if (!status) {
                return false;
            }
        }
        return true;
    }

    private void combineChunks(String fileName, int totalChunks) throws Exception {
        File outputFile = new File(TEMP_DIR + fileName);
        try (FileOutputStream fos = new FileOutputStream(outputFile, true)) {
            for (int i = 0; i < totalChunks; i++) {
                Path chunkPath = Paths.get(TEMP_DIR + fileName + "_" + i);
                byte[] chunkBytes = Files.readAllBytes(chunkPath);
                fos.write(chunkBytes);
                Files.delete(chunkPath); // Xóa chunk sau khi kết hợp
            }
        }
    }
}
