package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshRequest (

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String refreshToken

) {
}