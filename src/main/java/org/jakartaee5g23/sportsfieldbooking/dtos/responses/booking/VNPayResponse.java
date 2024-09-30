package org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking;

public record VNPayResponse(
        String code,
        String message,
        String paymentUrl
) {
}
