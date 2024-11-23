package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.rating.RatingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.rating.RatingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.*;
import org.jakartaee5g23.sportsfieldbooking.mappers.RatingMapper;
import org.jakartaee5g23.sportsfieldbooking.services.BookingItemsService;
import org.jakartaee5g23.sportsfieldbooking.services.RatingService;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("${api.prefix}/rating")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Rating APIs")
public class RatingController {
    RatingMapper ratingMapper;
    RatingService ratingService;
    SportsFieldService sportsFieldService;
    UserService userService;
    BookingItemsService bookingItemsService;

    @Operation(summary = "Get rating by booking item ID", description = "Retrieve rating by booking item ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/booking-item/{bookingItemId}")
    public ResponseEntity<RatingResponse> findByBookingItemId(@PathVariable String bookingItemId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingMapper.toRatingResponse(ratingService.findByBookingItemId(bookingItemId)));
    }

    @Operation(summary = "Get rating by booking items id", description = "Get rating by booking items id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> findById(@PathVariable String id) {
        return ResponseEntity.status(OK).body(ratingMapper.toRatingResponse(ratingService.findById(id)));
    }
    @Operation(summary = "Create a new rating", description = "Create a new rating", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/create")
    public ResponseEntity<RatingResponse> create(@RequestBody @Valid RatingRequest request) {
        SportsField sportsField = sportsFieldService.findById(request.sportsFieldId());
        User user = userService.findById(request.userId());
        BookingItem bookingItem = bookingItemsService.findById(request.bookingItemId());

        Rating rating = Rating.builder()
                .rating_point(request.rating_point())
                .user(user)
                .sportsField(sportsField)
                .bookingItem(bookingItem)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingMapper.toRatingResponse(ratingService.create(rating)));
    }

    @Operation(summary = "Get average rating by sports field ID", description = "Calculate average rating for a specific sports field", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/average/{sportsFieldId}")
    public ResponseEntity<Double> getAverageRatingBySportsFieldId(@PathVariable String sportsFieldId) {
        return ResponseEntity.status(OK)
                .body(ratingService.calculateAverageRatingBySportsFieldId(sportsFieldId));
    }
}
