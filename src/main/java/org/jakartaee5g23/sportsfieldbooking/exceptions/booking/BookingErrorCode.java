package org.jakartaee5g23.sportsfieldbooking.exceptions.booking;

import lombok.Getter;

@Getter
public enum BookingErrorCode {

    FIELD_AVAILABILITY_ORDERED("booking/availability-ordered", "field_availability_has_been_ordered"),

    SPORT_FIELD_NOT_AVAILABLE("booking/not-available","sport_field_not_available"),

    USER_BANNED("booking/user-banned", "user_is_banned"),

    BOOKING_FAILED("booking/failed", "booking_failed"),

    CANCEL_FAILED("booking/cancel-failed", "cancel_failed"),
    ;

    BookingErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}
