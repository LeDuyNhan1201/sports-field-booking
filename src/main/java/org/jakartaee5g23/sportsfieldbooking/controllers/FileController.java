package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.file.FileUploadRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.CommonResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/file")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "File APIs")
public class FileController {

    MinioClientService minioClientService;

    UserService userService;

    @Value("${minio.bucket-name}")
    @NonFinal
    String bucketName;

    @Operation(summary = "Get file metadata by user ID", description = "Retrieve file metadata by user ID")
    @GetMapping("/metadata-by-user")
    ResponseEntity<CommonResponse<String, ?>> getFileMetadataByUserId(@RequestParam String userId) {
        User current = userService.findById(userId);
        FileMetadata fileMetadata = minioClientService.getFileMetadataByUser(current);

        String avatarUrl = fileMetadata != null ? minioClientService.getObjectUrl(fileMetadata.getObjectKey()) : null;

        return ResponseEntity.ok(
                CommonResponse.<String, Object>builder()
                        .message(getLocalizedMessage("file_metadata_retrieved"))
                        .results(avatarUrl)
                        .build()
        );
    }

    @Operation(summary = "Upload chunk file", description = "Upload chunk file")
    @PostMapping("/upload-chunk")
    ResponseEntity<CommonResponse<Long, ?>> uploadChunk(@RequestPart(name = "file") MultipartFile file,
                                                        @RequestPart(name = "request") @Valid FileUploadRequest request) {

        long uploadedSize = minioClientService.uploadChunk(
                file,
                request.fileMetadataId(),
                request.chunkHash(),
                request.startByte(),
                request.totalSize(),
                request.contentType(),
                request.userId());

        HttpStatus httpStatus = uploadedSize == request.totalSize() ? CREATED : OK;

        String message = uploadedSize == request.totalSize() ? "file_upload_success" : "chunk_uploaded";

        return ResponseEntity.status(httpStatus).body(
                CommonResponse.<Long, Object>builder()
                        .message(getLocalizedMessage(message))
                        .results(uploadedSize)
                        .build()
        );
    }

    @Operation(summary = "Delete file", description = "Delete file")
    @DeleteMapping("/delete-file/{id}")
    ResponseEntity<CommonResponse<String, Object>> deleteObject(@PathVariable String id) {
        User current = userService.findById(id);
        current.setAvatar(null);
        userService.updateUser(current);

        return ResponseEntity.ok(
                CommonResponse.<String, Object>builder()
                        .message(getLocalizedMessage("file_metadata_retrieved"))
                        .build()
        );
    }

//    @Operation(summary = "Delete file", description = "Delete file")
//    @DeleteMapping("/delete-file")
//    ResponseEntity<CommonResponse<String, Object>> deleteObject(@RequestParam String objectKey) {
//        minioClientService.deleteObject(objectKey, bucketName);
//        return ResponseEntity.ok(
//                CommonResponse.<String, Object>builder()
//                        .message("File deleted successfully.")
//                        .results(null)
//                        .build()
//        );
//    }

//    private boolean isUploadComplete(boolean[] uploadStatus) {
//        for (boolean status : uploadStatus) {
//            if (!status) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void combineChunks(String fileName, int totalChunks) throws Exception {
//        File outputFile = new File(TEMP_DIR + fileName);
//        try (FileOutputStream fos = new FileOutputStream(outputFile, true)) {
//            for (int i = 0; i < totalChunks; i++) {
//                Path chunkPath = Paths.get(TEMP_DIR + fileName + "_" + i);
//                byte[] chunkBytes = Files.readAllBytes(chunkPath);
//                fos.write(chunkBytes);
//                Files.delete(chunkPath); // Xóa chunk sau khi kết hợp
//            }
//        }
//    }

}
