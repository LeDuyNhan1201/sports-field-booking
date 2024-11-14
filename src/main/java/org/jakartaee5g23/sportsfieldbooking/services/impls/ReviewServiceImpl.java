package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import org.jakartaee5g23.sportsfieldbooking.entities.Review;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.enums.NotificationType;
import org.jakartaee5g23.sportsfieldbooking.enums.UserStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.repositories.NotificationRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.ReviewRepository;
import org.jakartaee5g23.sportsfieldbooking.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;
    NotificationRepository notificationRepository;

    @Override
    public Review findById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Review"));
    }

    @Override
    public Page<Review> findBySportField(SportsField sportsField, int offset, int limit) {
        return reviewRepository.findBySportsField(sportsField, PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Review> findByParentReview(Review parentReview, int offset, int limit) {
        return reviewRepository.findByParentReview(parentReview, PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    public Review create(Review request) {
        if (request.getUser().getStatus().equals(UserStatus.BANNED)) throw new AppException(CommonErrorCode.USER_BANNED, HttpStatus.NOT_FOUND);

        Notification notification = Notification.builder()
                .user(request.getParentReview().getUser())
                .booking(null)
                .type(NotificationType.COMMENT_FEEDBACK)
                .message(getLocalizedMessage("message_confirmed"))
                .build();

        notificationRepository.save(notification);
        return reviewRepository.save(request);
    }

    @Override
    public Review updateComment(String id, String comment) {
        Review review = findById(id);
        review.setComment(comment);
        return reviewRepository.save(review);
    }

}
