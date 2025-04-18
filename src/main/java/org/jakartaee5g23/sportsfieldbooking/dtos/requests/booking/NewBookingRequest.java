package org.jakartaee5g23.sportsfieldbooking.dtos.requests.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record NewBookingRequest (

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String fieldAvailabilityId,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String sportFieldId,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        Date startTime,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        Date endTime

)
{ }
