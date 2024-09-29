package org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record NewSportFieldRequest (

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String name,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String location,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    @Min(value = 1, message = "min_field")
    Integer pricePerHour,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    @Size(min = 1, max = 20, message = "size_field")
    Integer opacity,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    Date closingTime,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    Date openingTime,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    Integer categoryId,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String userId,

    boolean isConfirmed

)
{ }