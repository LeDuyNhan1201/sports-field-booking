package org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSportsFieldRequest(

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String id,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String name,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String location,

        @NotNull(message = "null_field")
        @Min(value = 0, message = "min_value_field")
        Integer opacity,

        @NotNull(message = "null_field")
        Date closingTime,

        @NotNull(message = "null_field")
        Date openingTime,

        @NotNull(message = "null_field")
        Integer categoryId,

        Boolean isConfirmed

)
{ }
