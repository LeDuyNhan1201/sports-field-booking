package org.jakartaee5g23.sportsfieldbooking.dtos.requests.fieldAvailabilityAccess;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record FieldAvailabilityAccessRequest(
        @NotNull(message = "null_field")
        Date endTime,

        @NotNull(message = "null_field")
        Date startTime,

        @NotNull(message = "null_field")
        String sportsFieldId
) {
}
