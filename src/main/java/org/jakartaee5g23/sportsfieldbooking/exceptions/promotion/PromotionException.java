package org.jakartaee5g23.sportsfieldbooking.exceptions.promotion;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class PromotionException extends AppException {

    public PromotionException(PromotionErrorCode promotionErrorCode, HttpStatus httpStatus) {
        super(promotionErrorCode.getMessage(), httpStatus);
        this.promotionErrorCode = promotionErrorCode;
    }

    private final PromotionErrorCode promotionErrorCode;
}
