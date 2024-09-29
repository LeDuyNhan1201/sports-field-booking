package org.jakartaee5g23.sportsfieldbooking.exceptions.booking;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class BookingException extends AppException {

    public BookingException(BookingErrorCode bookingErrorCode, HttpStatus httpStatus) {
        super(bookingErrorCode.getMessage(), httpStatus);
        this.fileStorageErrorCode = bookingErrorCode;
    }

    private final BookingErrorCode fileStorageErrorCode;
}
