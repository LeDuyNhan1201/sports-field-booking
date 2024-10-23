package org.jakartaee5g23.sportsfieldbooking.exceptions.file;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class FileException extends AppException {

    public FileException(FileErrorCode fileErrorCode, HttpStatus httpStatus) {
        super(fileErrorCode.getMessage(), httpStatus);
        this.fileErrorCode = fileErrorCode;
    }

    private final FileErrorCode fileErrorCode;

}