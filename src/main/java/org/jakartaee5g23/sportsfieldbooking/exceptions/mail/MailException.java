package org.jakartaee5g23.sportsfieldbooking.exceptions.mail;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class MailException extends AppException {

    public MailException(MailErrorCode fileStorageErrorCode, HttpStatus httpStatus) {
        super(fileStorageErrorCode.getMessage(), httpStatus);
        this.fileStorageErrorCode = fileStorageErrorCode;
    }

    private final MailErrorCode fileStorageErrorCode;

}