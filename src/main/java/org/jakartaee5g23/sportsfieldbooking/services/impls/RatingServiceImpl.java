package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.jakartaee5g23.sportsfieldbooking.entities.Rating;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.repositories.BookingItemRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.RatingRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportsFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRepository;
import org.jakartaee5g23.sportsfieldbooking.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RatingServiceImpl implements RatingService {
    RatingRepository ratingRepository;
    UserRepository userRepository;
    SportsFieldRepository sportsFieldRepository;
    BookingItemRepository bookingItemRepository;
    @Override
    public Rating findById(String id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Rating"));
    }

    @Override
    public Rating create(Rating request) {
        User user = userRepository.findById(request.getUser().getId())
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "User"));

        SportsField sportsField = sportsFieldRepository.findById(request.getSportsField().getId())
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Sports field"));

        BookingItem bookingItem = bookingItemRepository.findById(request.getBookingItem().getId())
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Booking item"));

        boolean exists = ratingRepository.existsByBookingItemId(request.getBookingItem().getId());
        if (exists) {
            throw new AppException(CommonErrorCode.RATING_FAILED, HttpStatus.BAD_REQUEST, "Rating");
        }

        Rating rating = Rating.builder()
                .rating_point(request.getRating_point())
                .user(user)
                .sportsField(sportsField)
                .bookingItem(bookingItem)
                .build();

        return ratingRepository.save(rating);
    }

    @Override
    public Rating findByBookingItemId(String bookingItemId) {
        return ratingRepository.findByBookingItemId(bookingItemId)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Rating for booking item"));
    }

    @Override
    public Double calculateAverageRatingBySportsFieldId(String sportsFieldId) {
        List<Rating> ratings = ratingRepository.findBySportsFieldId(sportsFieldId);

        if (ratings.isEmpty()) {
            return 0.0;
        }

        double totalPoints = ratings.stream()
                .mapToDouble(Rating::getRating_point)
                .sum();

        return totalPoints / ratings.size();
    }

    @Override
    public Map<String, Double> calculateAverageRatingForAllFields() {
        List<Rating> ratingList = ratingRepository.findAll();
        Map<String, Double> totalRatingPoint = new HashMap<>();
        Map<String, Integer> countRatingPoint = new HashMap<>();
        Map<String, Double> result = new HashMap<>();

        for(Rating rating: ratingList) {
            String sportsFieldID = rating.getSportsField().getId();
            double point = rating.getRating_point();

            totalRatingPoint.put(sportsFieldID, totalRatingPoint.getOrDefault(sportsFieldID, 0.0) + point);
            countRatingPoint.put(sportsFieldID, countRatingPoint.getOrDefault(sportsFieldID, 0) + 1);
        }

        for(String sportsFieldID : totalRatingPoint.keySet()) {
            double average = totalRatingPoint.get(sportsFieldID) / countRatingPoint.get(sportsFieldID);
            result.put(sportsFieldID, average);
        }

        return result;
    }
}
