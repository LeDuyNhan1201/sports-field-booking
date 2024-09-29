package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.ListReviewRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.RespondRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.ReviewRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.RespondResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.ReviewResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Review;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.UserStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.review.ReviewErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.review.ReviewException;
import org.jakartaee5g23.sportsfieldbooking.repositories.ReviewRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRepository;
import org.jakartaee5g23.sportsfieldbooking.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewSeviceImpl implements ReviewService {
    ReviewRepository reviewRepository;
    UserRepository userRepository;
    SportFieldRepository sportFieldRepository;


    @Override
    public List<Review> getReviewsBySportField(ListReviewRequest request) {
        return reviewRepository.findBySportField_Id(request.sportFieldID());
    }

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        User user = userRepository.findById(request.userID())
                .orElseThrow(() -> new AppException(CommonErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (user.getStatus().equals(UserStatus.BANNED)) {
            throw new AppException(CommonErrorCode.USER_BANNED, HttpStatus.NOT_FOUND);
        }

        SportField sportField = sportFieldRepository.findById(request.sportFieldID())
                .orElseThrow(() -> new AppException(CommonErrorCode.SPORTFIELD_NOT_FOUND, HttpStatus.NOT_FOUND));

        Review review = Review.builder()
                .sportField(sportField)
                .user(user)
                .comment(request.comment())
                .parentReview(null)
                .build();
        reviewRepository.save(review);

        reviewRepository.findById(review.getId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND, HttpStatus.NOT_FOUND));

        return new ReviewResponse(getLocalizedMessage("review_success"));
    }

    @Override
    public RespondResponse respond(RespondRequest request) {
        Review parentReview = reviewRepository.findById(request.parentReviewID())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND, HttpStatus.NOT_FOUND));

        User user = userRepository.findById(request.userID())
                .orElseThrow(() -> new AppException(CommonErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        SportField sportField = sportFieldRepository.findById(request.sportFieldID())
                .orElseThrow(() -> new AppException(CommonErrorCode.SPORTFIELD_NOT_FOUND, HttpStatus.NOT_FOUND));


        Review createRespond = Review.builder()
                .sportField(sportField)
                .user(user)
                .comment(request.comment())
                .parentReview(parentReview)
                .build();

        reviewRepository.save(createRespond);

        return new RespondResponse(getLocalizedMessage("respond_success"));
    }

}
