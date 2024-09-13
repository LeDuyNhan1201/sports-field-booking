package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.FileUploadRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.FileResponse;
import org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageException;
import org.jakartaee5g23.sportsfieldbooking.services.LocalStorageService;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.UUID;

import static org.jakartaee5g23.sportsfieldbooking.enums.FileHandleAction.*;
import static org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageErrorCode.COULD_NOT_READ_FILE;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.KAFKA_TOPIC_HANDLE_FILE;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/media")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Media APIs")
public class FileController {

    LocalStorageService localStorageService;

    MinioClientService minioClientService;

    KafkaTemplate<String, String> kafkaTemplate;

    @Operation(summary = "Upload file", description = "Upload file")
    @PostMapping("/upload")
    ResponseEntity<FileResponse> upload(@RequestPart(name = "file") MultipartFile file,
                                        @RequestPart(name = "request") @Valid FileUploadRequest request) {
        String backupPath = localStorageService.storeFile(file, "tests/" + request.ownerId());

        kafkaTemplate.send(KAFKA_TOPIC_HANDLE_FILE, UPLOAD
                + ":" + backupPath + ":" + file.getSize() + ":" + file.getContentType());

        return ResponseEntity.status(CREATED).body(FileResponse.builder()
                .uuid(UUID.randomUUID().toString())
                .objectKey(backupPath)
                .type(file.getContentType())
                .size(file.getSize())
                .createdAt(new Date())
                .build()
        );
    }

    @Operation(summary = "Get folder", description = "Load all files of folder")
    @GetMapping("/get-folder")
    ResponseEntity<?> getFolder(@RequestParam String path) {
        try {
            return ResponseEntity.ok().body(minioClientService.loadAllFromParent(path));

        } catch (Exception e) {
            throw new FileStorageException(COULD_NOT_READ_FILE, BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete file", description = "Delete file")
    @DeleteMapping("/delete-file")
    @Transactional(rollbackFor = FileStorageException.class)
    void deleteObject(@RequestParam String objectKey) {
        localStorageService.deleteFile(objectKey);
        kafkaTemplate.send(KAFKA_TOPIC_HANDLE_FILE, DELETE_OBJECT + ":" + objectKey);
    }

    @Operation(summary = "Delete folder", description = "Delete folder")
    @DeleteMapping("/delete-folder")
    void deleteParentObject(@RequestParam String parentKey) {
        localStorageService.deleteFolder(parentKey);
        kafkaTemplate.send(KAFKA_TOPIC_HANDLE_FILE, DELETE_PARENT_OBJECT + ":" + parentKey);
    }

    @Operation(summary = "Show media", description = "Show media")
    @GetMapping("/show-media")
    public ResponseEntity<RedirectView> redirectToNewUrl(@RequestParam String objectKey) {
        RedirectView redirectView = new RedirectView();
        try {
            redirectView.setUrl(minioClientService.getObjectUrl(objectKey));
            return new ResponseEntity<>(redirectView, MOVED_PERMANENTLY);

        } catch (Exception e) {
            throw new FileStorageException(COULD_NOT_READ_FILE, BAD_REQUEST);
        }
    }

}
