package org.jakartaee5g23.sportsfieldbooking.controllers.consumers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.enums.FileHandleAction;
import org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageException;
import org.jakartaee5g23.sportsfieldbooking.services.LocalStorageService;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.InputStream;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageErrorCode.*;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.KAFKA_TOPIC_HANDLE_FILE;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AsyncFileController {

    LocalStorageService localStorageService;

    MinioClientService minioClientService;

    @KafkaListener(
            topics = KAFKA_TOPIC_HANDLE_FILE,
            groupId = "${spring.kafka.file-consumer.group-id}",
            errorHandler = "kafkaListenerErrorHandler"
    )
    public void listenFileHandleAction(String message) {
        String action = message.split(":")[0];
        String key = message.split(":")[1];

        log.info("Message received: {}", message);

        switch (FileHandleAction.valueOf(action)) {
            case UPLOAD -> {
                long size = Long.parseLong(message.split(":")[2]);
                String contentType = message.split(":")[3];
                InputStream inputStream = localStorageService.readFile(key);

                try {
                    minioClientService.storeObject(inputStream, size, contentType, key);

                } catch (Exception e) {
                    throw new FileStorageException(CAN_NOT_STORE_FILE, UNPROCESSABLE_ENTITY);
                }
            }
            case DELETE_OBJECT -> {
                try {
                    minioClientService.deleteObject(key);

                } catch (Exception e) {
                    throw new FileStorageException(CAN_NOT_DELETE_FILE, UNPROCESSABLE_ENTITY);
                }
            }
            case DELETE_PARENT_OBJECT -> {
                try {
                    minioClientService.deleteParentObject(key);

                } catch (Exception e) {
                    throw new FileStorageException(CAN_NOT_DELETE_FOLDER, UNPROCESSABLE_ENTITY);
                }
            }

        }
    }

}
