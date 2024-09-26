package org.jakartaee5g23.sportsfieldbooking.exceptions.booking;

import lombok.Getter;

@Getter
public enum BookingErrorCode {
    FIELD_AVAIBILITY_ORDERED("FIELD-AVAIBILITY-ORDERED", "field_avaiability_has_been_ordered"),
    FIELD_AVAIBILITY_NOT_FOUND("FIELD-AVAIBILITY-NOT-FOUND", "field_availability_not_found"),
    USER_NOT_FOUND("USER-NOT-FOUND", "user_not_found"),
    SPORTFIELD_NOT_FOUND("SPORTFIELD-NOT-FOUND","sport_field_not_found"),
    SPORTFIELD_NOT_AVAILABLE("SPORTFIELD-NOT-AVAILABLE","sport_field_not_available"),
    PAYMENT_NOT_FOUND("PAYMENT-NOT-FOUND","payment_not_found"),
    ORDER_NOT_FOUND("ORDER-NOT-FOUND","order_not_found"),
    USER_BANNED("USER-BANNED", "user_is_banned"),
    ;

    BookingErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}
