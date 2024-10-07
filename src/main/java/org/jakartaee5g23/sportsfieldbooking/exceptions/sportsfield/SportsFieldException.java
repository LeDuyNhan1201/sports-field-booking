package org.jakartaee5g23.sportsfieldbooking.exceptions.sportsfield;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class SportsFieldException extends AppException {

    public SportsFieldException(SportsFieldErrorCode sportsFieldErrorCode, HttpStatus httpStatus) {
        super(sportsFieldErrorCode.getMessage(), httpStatus);
        this.sportsFieldErrorCode = sportsFieldErrorCode;
    }

    private final SportsFieldErrorCode sportsFieldErrorCode;
}
