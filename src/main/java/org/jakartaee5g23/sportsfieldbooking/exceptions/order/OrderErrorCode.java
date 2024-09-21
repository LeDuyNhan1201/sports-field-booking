package org.jakartaee5g23.sportsfieldbooking.exceptions.order;

import lombok.Getter;

@Getter
public enum OrderErrorCode {
    ORDER_CHECK_PENDING("ORDER-CHECK-PENDING", "order_check_pending"),
    ;

    OrderErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;
}
