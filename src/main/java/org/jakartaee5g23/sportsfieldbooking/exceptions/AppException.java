package org.jakartaee5g23.sportsfieldbooking.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class AppException extends RuntimeException {

    public AppException(String Message, HttpStatus httpStatus) {
        super(Message);
        this.httpStatus = httpStatus;
    }

    @Setter
    Object[] moreInfo;
    final HttpStatus httpStatus;

}
