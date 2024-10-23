package org.jakartaee5g23.sportsfieldbooking.services.impls;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileException;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode.*;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.TEMP_DIR;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.isMedia;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@Slf4j
public class MinioClientServiceImpl implements MinioClientService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public MinioClientServiceImpl(@Value("${minio.endpoint}") String endpoint,
                                  @Value("${minio.access-key}") String accessKey,
                                  @Value("${minio.secret-key}") String secretKey) {
        this.minioClient = MinioClient.builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build();
    }

    @Override
    public FileMetadata uploadChunk(MultipartFile file, String fileName, int chunkNumber, int totalChunks) throws Exception {
        return null;
    }

    @Override
    public void storeObject(File file, String fileName, String contentType) throws Exception {
        ensureBucketExists(bucketName);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(Files.newInputStream(file.toPath()), file.length(), -1)
                .contentType(contentType)
                .build());
    }

    @Override
    public String getObjectUrl(String objectKey) throws Exception {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectKey)
                    .build());

        } catch (MinioException e) {
            throw new FileException(COULD_NOT_READ_FILE, BAD_REQUEST);
        }
    }

    @Override
    public void deleteObject(String objectKey) throws Exception {
        GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectKey)
                .build());
        if (response == null) throw new FileException(FILE_NOT_FOUND, BAD_REQUEST);

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectKey)
                .build());

        } catch (MinioException e) {
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
