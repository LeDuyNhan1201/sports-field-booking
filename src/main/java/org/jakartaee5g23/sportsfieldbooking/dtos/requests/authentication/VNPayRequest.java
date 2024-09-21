package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record VNPayRequest(
        long amount,

        String bankCode,

        String orderID
){
}
