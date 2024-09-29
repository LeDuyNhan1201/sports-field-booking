package org.jakartaee5g23.sportsfieldbooking.exceptions.review;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.payment.PaymentErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class ReviewException extends AppException {
    public ReviewException(ReviewErrorCode reviewErrorCode, HttpStatus httpStatus) {
        super(reviewErrorCode.getMessage(), httpStatus);
        this.reviewErrorCode = reviewErrorCode;
    }

    private final ReviewErrorCode reviewErrorCode;
}
