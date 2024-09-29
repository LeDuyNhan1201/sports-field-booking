package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.ListReviewRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.RespondRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.ReviewRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.RespondResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.ReviewResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Review;

import java.util.List;


public interface ReviewService {
    List<Review> getReviewsBySportField(ListReviewRequest sportFieldID);
    ReviewResponse createReview(ReviewRequest request);
    RespondResponse respond(RespondRequest request);
}
