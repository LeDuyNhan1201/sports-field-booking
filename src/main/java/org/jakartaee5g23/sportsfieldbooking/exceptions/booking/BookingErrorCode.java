package org.jakartaee5g23.sportsfieldbooking.exceptions.booking;

import lombok.Getter;

@Getter
public enum BookingErrorCode {
    SEND_MAIL_FAILED("BOOKING-001", "send_booking_error"),
    USER_NOT_FOUND("USER-NOT-FOUND", "user_not_found"),
    SPORTFIELD_NOT_FOUND("SPORTFIELD-NOT-FOUND","sport field not found"),
    PAYMENT_NOT_FOUND("PAYMENT-NOT-FOUND","payment not found"),
    ORDER_NOT_FOUND("ORDER-NOT-FOUND","order not found"),
    USER_BANNED("USER-BANNED", "user is banned"),
    SPORTFIELD_NONE("SPORTFIELD-NONE", "sport field is not available(currently PRE-ORDER or ORDER)")
    ;

    BookingErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}
