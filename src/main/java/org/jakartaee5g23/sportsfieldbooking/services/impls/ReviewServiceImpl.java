package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.Review;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.enums.UserStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.repositories.ReviewRepository;
import org.jakartaee5g23.sportsfieldbooking.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;

    @Override
    public Review findById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Review"));
    }

    @Override
    public Page<Review> findBySportField(SportField sportField, int offset, int limit) {
        return reviewRepository.findBySportField(sportField, PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Review> findByParentReview(Review parentReview, int offset, int limit) {
        return reviewRepository.findByParentReview(parentReview, PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    public Review create(Review request) {
        if (request.getUser().getStatus().equals(UserStatus.BANNED)) throw new AppException(CommonErrorCode.USER_BANNED, HttpStatus.NOT_FOUND);

        return reviewRepository.save(request);
    }

    @Override
    public Review updateComment(String id, String comment) {
        Review review = findById(id);
        review.setComment(comment);
        return reviewRepository.save(review);
    }

}
