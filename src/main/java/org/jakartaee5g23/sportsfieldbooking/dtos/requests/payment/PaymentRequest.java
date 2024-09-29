package org.jakartaee5g23.sportsfieldbooking.dtos.requests.payment;

public record PaymentRequest(
    Double amount,

    String orderID
) {
}
