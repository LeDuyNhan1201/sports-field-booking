package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record BookingRequest(
        @NotNull(message = "Field avaibility ID can't be null")

        String fieldAvaibilityID,

        @NotNull(message = "User ID can't be null")
        String idUser,

        @NotNull(message = "Sport field ID cannot be blank")
        String idSportField

) {}
