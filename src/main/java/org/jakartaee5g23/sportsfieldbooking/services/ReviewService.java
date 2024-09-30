package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Review;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    Review findById(String id);

    Page<Review> findBySportField(SportField sportField, int offset, int limit);

    Page<Review> findByParentReview(Review parentReview, int offset, int limit);

    Review create(Review request);

    Review updateComment(String id, String comment);

}
