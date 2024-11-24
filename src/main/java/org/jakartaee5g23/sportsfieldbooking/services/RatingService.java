package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface RatingService {
    Rating findById(String id);

    Rating create(Rating request);

    Rating findByBookingItemId(String bookingItemId);

    Double calculateAverageRatingBySportsFieldId(String sportsFieldId);

    Map<String, Double> calculateAverageRatingForAllFields();
}
