package org.jakartaee5g23.sportsfieldbooking.dtos.requests.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest (

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String sportFieldId,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String comment,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String userID,

        String parentReviewID

)
{ }
