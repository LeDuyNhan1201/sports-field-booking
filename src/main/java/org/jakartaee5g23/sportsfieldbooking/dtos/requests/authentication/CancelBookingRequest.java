package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import jakarta.validation.constraints.NotNull;

public record CancelBookingRequest(
        @NotNull(message = "Order ID not null")
        String orderID
) {
}
