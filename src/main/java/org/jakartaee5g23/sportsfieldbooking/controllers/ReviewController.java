package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.ListReviewRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.RespondRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.ReviewRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.ListReviewResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.RespondResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.ReviewResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Review;
import org.jakartaee5g23.sportsfieldbooking.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Review APIs")
public class ReviewController {
    ReviewService reviewService;

    @Operation(summary = "Create a comment", description = "Create a comment"
            , security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/createReview")
    public ResponseEntity<ReviewResponse> createReview(@RequestBody @Valid ReviewRequest request) {
        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Respond a comment", description = "Respond a comment"
            , security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/respondReview")
    public ResponseEntity<RespondResponse> respondToReview(@RequestBody RespondRequest request) {
        RespondResponse response = reviewService.respond(request);
        return ResponseEntity.ok(response);
    }




}
