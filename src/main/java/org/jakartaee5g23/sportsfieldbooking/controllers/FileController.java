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
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
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

    @Operation(summary = "Upload chunk file", description = "Upload chunk file")
    @PostMapping("/upload-chunk")
    ResponseEntity<CommonResponse<String, ?>> uploadChunk(@RequestPart(name = "file") MultipartFile file,
                                               @RequestPart(name = "request") @Valid FileUploadRequest request) {

        String message = minioClientService.uploadChunk(
                file,
                request.fileName(),
                request.chunkNumber(),
                request.totalChunks(),
                request.contentType());
        return ResponseEntity.status(CREATED).body(
                CommonResponse.<String, Object>builder()
                        .message(getLocalizedMessage(message))
                        .results(message.equals("file_upload_success") ? "uploaded" : "uploading")
                        .build()
        );
    }

    @Operation(summary = "Delete file", description = "Delete file")
    @DeleteMapping("/delete-file")
    void deleteObject(@RequestParam String objectKey) {
        //localStorageService.deleteFile(objectKey);
    }

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
