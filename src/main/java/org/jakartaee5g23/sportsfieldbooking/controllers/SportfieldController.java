package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.ListReviewRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.ListReviewResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Review;
import org.jakartaee5g23.sportsfieldbooking.services.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/sport-field")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Sport Field APIs")
public class SportfieldController {
    ReviewService reviewService;

    @Operation(summary = "View reviews in each sport field", description = "View reviews in each sport field")
    @GetMapping("/{sport-field-id}")
    public ListReviewResponse getReviewsBySportField(@RequestParam("sportFieldID") ListReviewRequest request) {
        List<Review> reviews = reviewService.getReviewsBySportField(request);
        return new ListReviewResponse(reviews);
    }
}
