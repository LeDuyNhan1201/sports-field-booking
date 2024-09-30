package org.jakartaee5g23.sportsfieldbooking.dtos.requests.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest (

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    @Min(value = 1, message = "min_field")
    Double amount,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String orderId

)
{ }
