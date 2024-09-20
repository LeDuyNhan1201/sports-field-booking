package org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking;

import org.springframework.http.HttpStatus;

public record BookingResponse(
        int errorCode,

        String message
) {
}
