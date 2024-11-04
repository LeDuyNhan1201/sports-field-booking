package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.ReviewRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.ReviewResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Review;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.mappers.ReviewMapper;
import org.jakartaee5g23.sportsfieldbooking.services.ReviewService;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.getUserIdFromContext;

@RestController
@RequestMapping("${api.prefix}/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Review APIs")
public class ReviewController {

    ReviewService reviewService;

    UserService userService;

    SportsFieldService sportsFieldService;

    ReviewMapper reviewMapper = ReviewMapper.INSTANCE;

    @Operation(summary = "Create a comment", description = "Create a comment", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<ReviewResponse> create(@RequestBody @Valid ReviewRequest request) {
        User current = userService.findById(request.userID());
        SportsField sportsField = sportsFieldService.findById(request.sportFieldId());
        Review review = reviewMapper.toReview(request);
        review.setUser(current);
        review.setSportsField(sportsField);
        return ResponseEntity.ok(reviewMapper.toReviewResponse(reviewService.create(review)));
    }

    @Operation(summary = "Respond a comment", description = "Respond a comment", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/{parentId}")
    public ResponseEntity<ReviewResponse> respond(@PathVariable String parentId, @RequestBody @Valid ReviewRequest request) {
        User current = userService.findById(request.userID());
        Review parentReview = reviewService.findById(parentId);
        SportsField sportsField = parentReview.getSportsField();
        Review review = reviewMapper.toReview(request);
        review.setParentReview(parentReview);
        review.setSportsField(sportsField);
        review.setUser(current);

        return ResponseEntity.ok(reviewMapper.toReviewResponse(reviewService.create(review)));
    }


    @Operation(summary = "Update a review", description = "Update a review", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    @PostAuthorize("returnObject.body.MUser.id == authentication.name")
    public ResponseEntity<ReviewResponse> update(@PathVariable String id, @RequestParam String comment) {
        return ResponseEntity.ok(reviewMapper.toReviewResponse(reviewService.updateComment(id, comment)));
    }

    @Operation(summary = "Get review list", description = "Get review list", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{sportFieldId}")
    public ResponseEntity<PaginateResponse<ReviewResponse>> findBySportField(@PathVariable String sportFieldId,
                                                                            @RequestParam(defaultValue = "0") String offset,
                                                                            @RequestParam(defaultValue = "100") String limit) {
        SportsField sportsField = sportsFieldService.findById(sportFieldId);
        Page<Review> reviews = reviewService.findBySportField(sportsField, Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseEntity.status(HttpStatus.OK)
                .body(PaginateResponse.<ReviewResponse>builder()
                        .items(reviews.stream().map(reviewMapper::toReviewResponse).toList())
                        .pagination(new Pagination(Integer.parseInt(offset), Integer.parseInt(limit), reviews.getTotalElements()))
                        .build());
    }

    @Operation(summary = "Get reply list", description = "Get reply list", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/replies/{parentId}")
    public ResponseEntity<PaginateResponse<ReviewResponse>> getBookingList(@PathVariable String parentId,
                                                                           @RequestParam(defaultValue = "0") String offset,
                                                                           @RequestParam(defaultValue = "100") String limit) {
        Review parentReview = reviewService.findById(parentId);
        Page<Review> reviews = reviewService.findByParentReview(parentReview, Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseEntity.status(HttpStatus.OK)
                .body(PaginateResponse.<ReviewResponse>builder()
                        .items(reviews.stream().map(reviewMapper::toReviewResponse).toList())
                        .pagination(new Pagination(Integer.parseInt(offset), Integer.parseInt(limit), reviews.getTotalElements()))
                        .build());
    }

}
