package org.jakartaee5g23.sportsfieldbooking.dtos.requests.review;

import org.jakartaee5g23.sportsfieldbooking.entities.Review;

public record RespondRequest(
        String parentReviewID,

        String userID,
        String sportFieldID,
        String comment

) {
}
