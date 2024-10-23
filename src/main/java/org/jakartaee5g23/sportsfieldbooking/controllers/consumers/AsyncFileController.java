package org.jakartaee5g23.sportsfieldbooking.controllers.consumers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.enums.FileHandleAction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.KAFKA_TOPIC_HANDLE_FILE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AsyncFileController {

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

            }
            case DELETE_OBJECT -> {

            }
        }
    }

}
