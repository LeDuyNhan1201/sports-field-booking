package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import org.jakartaee5g23.sportsfieldbooking.enums.PaymentMethod;

public record PaymentRequest(
    Double amount,

    String orderID
) {
}
