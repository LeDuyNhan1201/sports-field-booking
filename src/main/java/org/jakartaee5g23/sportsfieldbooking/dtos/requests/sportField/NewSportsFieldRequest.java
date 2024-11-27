package org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;

public record NewSportsFieldRequest(

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


    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String userId,

    Boolean isConfirmed

)
{ }