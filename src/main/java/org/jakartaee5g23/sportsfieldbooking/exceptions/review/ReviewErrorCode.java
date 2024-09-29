package org.jakartaee5g23.sportsfieldbooking.exceptions.review;


import lombok.Getter;

@Getter
public enum ReviewErrorCode {
    REVIEW_NOT_FOUND("REVIEW-NOT-FOUND","review_not_found");

    ;
    ReviewErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;
}
