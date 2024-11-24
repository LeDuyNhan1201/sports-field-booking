package org.jakartaee5g23.sportsfieldbooking.dtos.requests.rating;

import jakarta.validation.constraints.NotNull;


public record RatingRequest (
        @NotNull(message = "null_field")
        Double rating_point,

        @NotNull(message = "null_field")
        String userId,

        @NotNull(message = "null_field")
        String sportsFieldId,

        @NotNull(message = "null_field")
        String bookingItemId
){
}
