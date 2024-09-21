package org.jakartaee5g23.sportsfieldbooking.exceptions.order;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.springframework.http.HttpStatus;

@Getter
public class OrderException extends AppException {
    public OrderException(OrderErrorCode orderErrorCode, HttpStatus httpStatus) {
        super(orderErrorCode.getMessage(), httpStatus);
        this.orderErrorCode = orderErrorCode;
    }

    private final OrderErrorCode orderErrorCode;
}
