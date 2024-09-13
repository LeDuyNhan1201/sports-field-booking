package org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class FileStorageException extends AppException {

    public FileStorageException(FileStorageErrorCode fileStorageErrorCode, HttpStatus httpStatus) {
        super(fileStorageErrorCode.getMessage(), httpStatus);
        this.fileStorageErrorCode = fileStorageErrorCode;
    }

    private final FileStorageErrorCode fileStorageErrorCode;

}