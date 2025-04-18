package org.jakartaee5g23.sportsfieldbooking.controllers.consumers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.events.HandleFileEvent;
import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileException;
import org.jakartaee5g23.sportsfieldbooking.mappers.FileMetadataMapper;
import org.jakartaee5g23.sportsfieldbooking.services.FileMetadataService;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode.FILE_NOT_FOUND;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.KAFKA_TOPIC_HANDLE_FILE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.kafka.retrytopic.TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TOPIC;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HandleFileTopicConsumer {

    FileMetadataService fileMetadataService;

    MinioClientService minioClientService;

    @Value("${minio.bucket-name}")
    @NonFinal
    String bucketName;

    UserService userService;

    SportsFieldService sportsFieldService;

    FileMetadataMapper fileMetadataMapper = FileMetadataMapper.INSTANCE;

    @KafkaListener(
            topics = KAFKA_TOPIC_HANDLE_FILE,
            groupId = "${spring.kafka.file-consumer.group-id}",
            containerFactory = "handleFileContainerFactory",
            errorHandler = "kafkaListenerErrorHandler"
    )
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1000, multiplier = 1.0, maxDelay = 5000),
            topicSuffixingStrategy = SUFFIX_WITH_INDEX_VALUE,
            kafkaTemplate = "handleFileTemplate",
            retryTopicSuffix = "-retrytopic",
            dltTopicSuffix = "-dlttopic",
            include = { AppException.class }
    )
    public void listenFileHandleAction(HandleFileEvent message, Acknowledgment acknowledgment) {
        log.info("Message received: {}", message.getObjectKey());

        User user;
        SportsField sportsField;
        FileMetadata fileMetadata = fileMetadataMapper.toFileMetadata(message);

        switch (message.getAction()) {
            case UPLOAD -> {
                switch (message.getType()) {
                    case USER_AVATAR -> {
                        user = userService.findById(message.getOwnerId());
                        if (user.getAvatar() != null) {
                            FileMetadata oldAvatar = fileMetadataService.findById(user.getAvatar().getId());
                            String objectKey = oldAvatar.getObjectKey();
                            oldAvatar.setObjectKey(fileMetadata.getObjectKey());
                            fileMetadataService.update(oldAvatar);
                            minioClientService.deleteObject(objectKey, bucketName);

                        } else {
                            fileMetadata.setUser(user);
                            fileMetadataService.create(fileMetadata);
                        }

                    } case SPORTS_FIELD_IMAGE -> {
                        sportsField = sportsFieldService.findById(message.getOwnerId());
                        fileMetadata.setSportsField(sportsField);
                        fileMetadataService.create(fileMetadata);

                    } default -> {
                        log.error("Invalid file metadata type: {}", message.getType());
                        throw new FileException(FILE_NOT_FOUND, NOT_FOUND);
                    }
                }
            }

            case DELETE -> {
                if (fileMetadata.getId() != null) {
                    FileMetadata fileMetadataToDelete = fileMetadataService.findById(fileMetadata.getId());
                    fileMetadataService.delete(fileMetadataToDelete);
                }
            }
        }

        acknowledgment.acknowledge();
    }

    @DltHandler
    public void dtl(HandleFileEvent message, @Header(RECEIVED_TOPIC) String topic) {
        log.info("DTL TOPIC message : {}, topic name : {}", message.getObjectKey(), topic);
    }

}
