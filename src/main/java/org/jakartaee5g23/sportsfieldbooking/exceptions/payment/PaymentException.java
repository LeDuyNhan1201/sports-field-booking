package org.jakartaee5g23.sportsfieldbooking.exceptions.payment;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class PaymentException extends AppException {

    public PaymentException(PaymentErrorCode paymentErrorCode, HttpStatus httpStatus) {
        super(paymentErrorCode.getMessage(), httpStatus);
        this.paymentErrorCode = paymentErrorCode;
    }

    private final PaymentErrorCode paymentErrorCode;

}