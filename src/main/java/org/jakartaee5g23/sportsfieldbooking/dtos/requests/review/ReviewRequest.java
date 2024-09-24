package org.jakartaee5g23.sportsfieldbooking.dtos.requests.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest (
        @NotNull(message = "User ID can not be null")
        String userID,

        @NotNull(message = "Sport field ID can not be null")
        String sportFieldID,

        @NotNull(message = "Sport field ID can not be null")
        @NotBlank(message = "Sport field ID can not be blank")
        String comment
){
}
