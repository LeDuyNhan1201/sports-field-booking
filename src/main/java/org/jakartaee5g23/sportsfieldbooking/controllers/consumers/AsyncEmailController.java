package org.jakartaee5g23.sportsfieldbooking.controllers.consumers;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.enums.VerificationType;
import org.jakartaee5g23.sportsfieldbooking.exceptions.mail.MailException;
import org.jakartaee5g23.sportsfieldbooking.services.MailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.mail.MailErrorCode.SEND_MAIL_ERROR;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.KAFKA_TOPIC_SEND_MAIL;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AsyncEmailController {

    MailService mailService;

    @KafkaListener(
            topics = KAFKA_TOPIC_SEND_MAIL,
            groupId = "${spring.kafka.mail-consumer.group-id}",
            errorHandler = "kafkaListenerErrorHandler")
    public void listenNotificationDelivery(String message) {
        String type = message.split(":")[0];
        String email = message.split(":")[1];
        String token = message.split(":")[2];
        String code = message.split(":")[3];

        log.info("Message received: {}", message);

        try {
            switch (VerificationType.valueOf(type)) {
                case VERIFY_EMAIL_BY_CODE -> mailService.sendMailToVerifyWithCode(email, code);

                case VERIFY_EMAIL_BY_TOKEN -> mailService.sendMailToVerifyWithToken(email, token);

                case RESET_PASSWORD -> mailService.sendMailToResetPassword(email, code);
            }
        }
        catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailException(SEND_MAIL_ERROR, UNPROCESSABLE_ENTITY);
        }
    }

}
