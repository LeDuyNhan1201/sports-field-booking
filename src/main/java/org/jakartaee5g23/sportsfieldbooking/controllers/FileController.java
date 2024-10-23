package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.file.FileUploadRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.CommonResponse;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileException;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.TEMP_DIR;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/file")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "File APIs")
public class FileController {

    MinioClientService minioClientService;

    KafkaTemplate<String, String> kafkaTemplate;

    ConcurrentHashMap<String, boolean[]> uploadStatusMap = new ConcurrentHashMap<>();

    @Operation(summary = "Upload file", description = "Upload file")
    @PostMapping("/upload-chunk")
    ResponseEntity<CommonResponse<?, ?>> uploadChunk(@RequestPart(name = "file") MultipartFile file,
                                               @RequestPart(name = "request") @Valid FileUploadRequest request) {

        // Lưu trạng thái upload
        uploadStatusMap.putIfAbsent(request.fileName(), new boolean[request.totalChunks()]);
        boolean[] uploadStatus = uploadStatusMap.get(request.fileName());

        // Nếu chunk đã upload trước đó thì bỏ qua
        if (uploadStatus[request.chunkNumber()])
            return ResponseEntity.status(CREATED).body(
                    CommonResponse.builder()
                            .message(getLocalizedMessage("chunk_already_exists"))
                            .build()
            );

        // Tạo thư mục tạm nếu chưa có
        Path tempDir = Paths.get(TEMP_DIR);
        if (!Files.exists(tempDir)) {
            try {
                Files.createDirectories(tempDir);
            } catch (IOException e) {
                log.error("Error creating temp directory", e);
                throw new FileException(FileErrorCode.CAN_NOT_INIT_BACKUP_FOLDER, INTERNAL_SERVER_ERROR);
            }
        }

        // Lưu chunk vào thư mục tạm
        File chunkFile = new File(TEMP_DIR + request.fileName() + "_" + request.chunkNumber());
        try (FileOutputStream fos = new FileOutputStream(chunkFile)) {
            fos.write(file.getBytes());

        } catch (IOException e) {
            log.error("Error writing chunk to disk", e);
            throw new FileException(FileErrorCode.COULD_NOT_WRITE_CHUNK, INTERNAL_SERVER_ERROR);
        }

        // Đánh dấu chunk này đã được upload
        uploadStatus[request.chunkNumber()] = true;

        // Kiểm tra nếu đã nhận đủ các chunk, kết hợp lại
        if (isUploadComplete(uploadStatus)) {
            try {
                combineChunks(request.fileName(), request.totalChunks());
            } catch (Exception e) {
                log.error("Error combining chunks", e);
                throw new FileException(FileErrorCode.COULD_NOT_COMBINE_CHUNKS, INTERNAL_SERVER_ERROR);
            }

            File fileAfterCombine = new File(TEMP_DIR + request.fileName());
            try {
                minioClientService.storeObject(fileAfterCombine, request.fileName(), request.contentType());
                Files.delete(fileAfterCombine.toPath()); // Xóa file sau khi upload hoàn thành
            } catch (Exception e) {
                log.error("Error storing file", e);
                throw new FileException(FileErrorCode.CAN_NOT_STORE_FILE, INTERNAL_SERVER_ERROR);
            }
            // Xóa trạng thái sau khi upload hoàn thành
            uploadStatusMap.remove(request.fileName());
        }

        return ResponseEntity.status(CREATED).body(
                CommonResponse.builder()
                        .message(getLocalizedMessage("chunk_uploaded"))
                        .build()
        );
    }

    @Operation(summary = "Delete file", description = "Delete file")
    @DeleteMapping("/delete-file")
    void deleteObject(@RequestParam String objectKey) {
        //localStorageService.deleteFile(objectKey);
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
