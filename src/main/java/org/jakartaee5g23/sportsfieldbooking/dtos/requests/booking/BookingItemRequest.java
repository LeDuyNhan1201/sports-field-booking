package org.jakartaee5g23.sportsfieldbooking.dtos.requests.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record BookingItemRequest(
        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String orderId,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String fieldAvailabilityId,

        @NotNull(message = "null_field")
        Date availableDate,

        @NotNull(message = "null_field")
        Date startTime,

        @NotNull(message = "null_field")
        Date endTime,

        @NotNull(message = "null_field")
        Double price
) {
}
