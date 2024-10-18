package org.jakartaee5g23.sportsfieldbooking.exceptions.promotion;

import lombok.Getter;

@Getter
public enum PromotionErrorCode {

    INVALID_START_END_TIME("promotion/invalid_start_end_time", "opening_hours_must_not_be_greater_than_closing_hours"),

    CREATE_FAILED("promotion/create_failed", "create_failed"),

    UPDATE_FAILED("promotion/update_failed", "update_failed"),
    ;

    PromotionErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}
