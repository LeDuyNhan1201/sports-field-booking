package org.jakartaee5g23.sportsfieldbooking.exceptions;

import lombok.Getter;

@Getter
public enum CommonErrorCode {
    USER_BANNED("USER-BANNED", "user_banned"),
    OBJECT_NOT_FOUND("OBJECT-NOT-FOUND", "object_not_found"),
    ;

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}
