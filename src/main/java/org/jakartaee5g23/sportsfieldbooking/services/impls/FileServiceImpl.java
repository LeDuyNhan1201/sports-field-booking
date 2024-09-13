package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageException;
import org.jakartaee5g23.sportsfieldbooking.repositories.FileMetadataRepository;
import org.jakartaee5g23.sportsfieldbooking.services.FileService;
import org.jakartaee5g23.sportsfieldbooking.services.LocalStorageService;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.jakartaee5g23.sportsfieldbooking.enums.FileHandleAction.*;
import static org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageErrorCode.CAN_NOT_STORE_FILE;
import static org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageErrorCode.COULD_NOT_READ_FILE;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.KAFKA_TOPIC_HANDLE_FILE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileServiceImpl<T> implements FileService<T> {

    FileMetadataRepository fileMetadataRepository;

    LocalStorageService localStorageService;

    MinioClientService minioClientService;

    KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public FileMetadata findById(String id) {
        return fileMetadataRepository.findById(id).orElseThrow(() -> new FileStorageException(COULD_NOT_READ_FILE, BAD_REQUEST));
    }

    @Override
    public String getObjectUrl(FileMetadata file) {
        String objectKey = file.getObjectKey();
        try {
            return minioClientService.getObjectUrl(objectKey);

        } catch (Exception e) {
            throw new FileStorageException(COULD_NOT_READ_FILE, BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public void uploadFiles(T owner, List<MultipartFile> files) {
        List<FileMetadata> results = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileId = UUID.randomUUID().toString();

            String backupPath = localStorageService.storeFile(file, getPrefixPath(owner) + "/" + fileId);

            kafkaTemplate.send(KAFKA_TOPIC_HANDLE_FILE, UPLOAD
                    + ":" + backupPath + ":" + file.getSize() + ":" + file.getContentType());

            switch (owner.getClass().getSimpleName()) {
                case "User" -> results.add(FileMetadata.builder()
                        .user((User) owner)
                        .objectKey(backupPath)
                        .contentType(file.getContentType())
                        .size(file.getSize())
                        .build());
            }
        }

        fileMetadataRepository.saveAll(results);
    }

    @Override
    public void deleteFile(String fileId) {
        String objectKey = findById(fileId).getObjectKey();
        localStorageService.deleteFile(objectKey);
        fileMetadataRepository.deleteById(fileId);
        kafkaTemplate.send(KAFKA_TOPIC_HANDLE_FILE, DELETE_OBJECT + ":" + objectKey);
    }

    @Override
    public void deleteFolder(T owner) {
        localStorageService.deleteFolder(getPrefixPath(owner));
        switch (owner.getClass().getSimpleName()) {
            case "User" -> fileMetadataRepository.delete(((User) owner).getAvatar());
        }
        kafkaTemplate.send(KAFKA_TOPIC_HANDLE_FILE, DELETE_PARENT_OBJECT + ":" + getPrefixPath(owner));
    }

    private String getPrefixPath(T owner) {
        return switch (owner.getClass().getSimpleName()) {
            case "User" -> "users/" + ((User) owner).getId();
            default -> throw new FileStorageException(CAN_NOT_STORE_FILE, BAD_REQUEST);
        };
    }

}
