package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record SportFieldRequest(
        String id,

        @NotNull(message = "Opacity can't be null") Integer opacity,

        @NotNull(message = "Opacity can't be null") Integer pricePerHour,

        Date closingTime,

        Date openingTime,

        String location,

        String name,

        boolean isConfirmed) {
}
