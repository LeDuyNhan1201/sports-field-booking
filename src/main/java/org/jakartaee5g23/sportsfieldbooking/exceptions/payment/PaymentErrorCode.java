package org.jakartaee5g23.sportsfieldbooking.exceptions.payment;

import lombok.Getter;

@Getter
public enum PaymentErrorCode {
    PAYMENT_AMOUNT_INVALID("PAYMENT_AMOUNT_INVALID", "payment_amount_invalid"),
    PAYMENT_ORDER_NOT_FOUND("PAYMENT_ORDER_NOT_FOUND", "payment_order_not_found")

    ;
    PaymentErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;
}