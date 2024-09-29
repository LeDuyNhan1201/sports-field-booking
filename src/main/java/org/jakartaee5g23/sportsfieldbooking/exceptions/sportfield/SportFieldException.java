package org.jakartaee5g23.sportsfieldbooking.exceptions.sportfield;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class SportFieldException extends AppException {

    public SportFieldException(SportFieldErrorCode sportFieldErrorCode, HttpStatus httpStatus) {
        super(sportFieldErrorCode.getMessage(), httpStatus);
        this.sportFieldErrorCode = sportFieldErrorCode;
    }

    private final SportFieldErrorCode sportFieldErrorCode;
}
