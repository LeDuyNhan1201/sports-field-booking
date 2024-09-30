package org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSportFieldRequest (

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String id,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String location,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String name,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        Integer opacity,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        Integer pricePerHour,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String closingTime,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String openingTime,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        Integer categoryId,

        Boolean isConfirmed

)
{ }
