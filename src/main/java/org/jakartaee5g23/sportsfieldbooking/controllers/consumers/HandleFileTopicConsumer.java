package org.jakartaee5g23.sportsfieldbooking.controllers.consumers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.events.HandleFileEvent;
import org.jakartaee5g23.sportsfieldbooking.entities.AppFileMetadata;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.mappers.FileMetadataMapper;
import org.jakartaee5g23.sportsfieldbooking.services.FileMetadataService;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.KAFKA_TOPIC_HANDLE_FILE;
import static org.springframework.kafka.retrytopic.TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TOPIC;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HandleFileTopicConsumer {

    FileMetadataService fileMetadataService;

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
        AppFileMetadata fileMetadata = fileMetadataMapper.toFileMetadata(message);

        if (message.getType() != null) {
            switch (message.getType()) {
                case USER_AVATAR -> {
                    user = userService.findById(message.getOwnerId());
                    fileMetadata.setUser(user);

                } case SPORTS_FIELD_IMAGE -> {
                    sportsField = sportsFieldService.findById(message.getOwnerId());
                    fileMetadata.setSportsField(sportsField);

                } default -> {
                    fileMetadata.setUser(null);
                    fileMetadata.setSportsField(null);
                }
            }
        } else {
            fileMetadata.setUser(null);
            fileMetadata.setSportsField(null);
        }


        switch (message.getAction()) {
            case UPLOAD -> fileMetadataService.create(fileMetadata);

            case DELETE -> {
                AppFileMetadata fileMetadataToDelete = fileMetadataService.findById(fileMetadata.getId());
                fileMetadataService.delete(fileMetadataToDelete);
            }
        }

        acknowledgment.acknowledge();
    }

    @DltHandler
    public void dtl(HandleFileEvent message, @Header(RECEIVED_TOPIC) String topic) {
        log.info("DTL TOPIC message : {}, topic name : {}", message.getObjectKey(), topic);
    }

}
