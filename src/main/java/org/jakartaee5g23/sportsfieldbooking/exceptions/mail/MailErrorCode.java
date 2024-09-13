package org.jakartaee5g23.sportsfieldbooking.exceptions.mail;

import lombok.Getter;

@Getter
public enum MailErrorCode {

    SEND_MAIL_ERROR("mail/send-mail-error", "send_mail_error"),
    ;

    MailErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}