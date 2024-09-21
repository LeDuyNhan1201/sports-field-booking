package org.jakartaee5g23.sportsfieldbooking.dtos.responses;

import org.springframework.http.HttpStatus;

public record PaymentResponse(
        String message,

        HttpStatus status
) {
}
