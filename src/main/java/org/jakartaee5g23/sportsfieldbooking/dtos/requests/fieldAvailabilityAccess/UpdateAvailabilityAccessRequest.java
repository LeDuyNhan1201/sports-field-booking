package org.jakartaee5g23.sportsfieldbooking.dtos.requests.fieldAvailabilityAccess;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAvailabilityAccessRequest(
        @NotNull(message = "null_field")
        String id,

        @NotNull(message = "null_field")
        Date endDate,

        @NotNull(message = "null_field")
        Date startDate
) {}

