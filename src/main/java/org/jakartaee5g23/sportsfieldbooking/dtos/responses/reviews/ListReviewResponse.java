package org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews;

import org.jakartaee5g23.sportsfieldbooking.entities.Review;

import java.util.List;

public record ListReviewResponse(
        List<Review> reviews
) {
}
