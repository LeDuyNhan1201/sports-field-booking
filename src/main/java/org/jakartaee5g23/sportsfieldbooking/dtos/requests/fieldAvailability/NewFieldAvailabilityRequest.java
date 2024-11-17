package org.jakartaee5g23.sportsfieldbooking.dtos.requests.fieldAvailability;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewFieldAvailabilityRequest(
        @NotNull(message = "null_field")
        Double price,

        @NotNull(message = "null_field")
        Date endTime,

        @NotNull(message = "null_field")
        Date startTime,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String sportsFieldId,

        Boolean isConfirmed
) {}
