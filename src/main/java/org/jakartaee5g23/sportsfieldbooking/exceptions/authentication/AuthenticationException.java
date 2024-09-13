package org.jakartaee5g23.sportsfieldbooking.exceptions.authentication;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class AuthenticationException extends AppException {

    public AuthenticationException(AuthenticationErrorCode authenticationErrorCode, HttpStatus httpStatus) {
        super(authenticationErrorCode.getMessage(), httpStatus);
        this.authenticationErrorCode = authenticationErrorCode;
    }

    private final AuthenticationErrorCode authenticationErrorCode;

}