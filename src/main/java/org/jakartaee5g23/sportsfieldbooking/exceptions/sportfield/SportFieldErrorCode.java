package org.jakartaee5g23.sportsfieldbooking.exceptions.sportfield;

import lombok.Getter;

@Getter
public enum SportFieldErrorCode {

    INVALID_OPENING_CLOSING_TIME("sort-field/invalid_opening_closing_time", "opening_hours_must_not_be_greater_than_closing_hours"),

    CREATE_FAILED("sort-field/create_failed", "create_failed"),

    UPDATE_FAILED("sort-field/update_failed", "update_failed"),
    ;

    SportFieldErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}
