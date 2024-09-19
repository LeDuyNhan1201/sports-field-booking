package org.jakartaee5g23.sportsfieldbooking.exceptions;

import lombok.Getter;

@Getter
public enum CommonErrorCode {
    OBJECT_NOT_FOUND("USER-NOT-FOUND", "object_not_found"),
    PAYMENT_NOT_FOUND("PAYMENT-NOT-FOUND","payment not found"),
    ;

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}
