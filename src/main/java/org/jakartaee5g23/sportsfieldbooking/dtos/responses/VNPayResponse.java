package org.jakartaee5g23.sportsfieldbooking.dtos.responses;

public record VNPayResponse(
        String code,
        String message,
        String paymentUrl
) {
}
