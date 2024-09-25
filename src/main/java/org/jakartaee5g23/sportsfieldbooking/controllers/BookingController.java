package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.BookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.CancelBookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.CancelBookingResponse;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/booking")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Booking APIs")
public class BookingController {
    BookingService bookingService;

    @Operation(summary = "Confirm booking", description = "Save booking's order", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/confirm-booking")
    public ResponseEntity<BookingResponse> booking(@RequestBody @Valid BookingRequest request) {
        BookingResponse response = bookingService.getBookingConfirmation(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Cancel booking", description = "Cancel booking after ordered", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/cancel-booking")
    public ResponseEntity<CancelBookingResponse> cancelBooking(@RequestBody @Valid CancelBookingRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.cancelBooking(request));
    }

    @Operation(summary = "View upcoming bookings", description = "Get list of upcoming bookings", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/upcoming")
    public ResponseEntity<List<BookingResponse>> getUpcomingBookingsByUserId(@RequestParam String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getUpcomingBookingsByUserId(userId));
    }

    @Operation(summary = "View booking history", description = "Get list of past bookings", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/booking-history")
    public ResponseEntity<List<BookingResponse>> getBookingHistory(@RequestParam("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getBookingHistory(userId));
    }

    @Operation(summary = "Reschedule booking", description = "Reschedule an existing booking", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/reschedule-booking/{bookingId}")
    public ResponseEntity<BookingResponse> rescheduleBooking(@PathVariable String bookingId,
            @RequestBody @Valid BookingRequest newBookingRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookingService.rescheduleBooking(bookingId, newBookingRequest));
    }

    @Operation(summary = "Request refund", description = "Request a refund for a booking", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/request-refund/{bookingId}")
    public ResponseEntity<BookingResponse> requestRefund(@PathVariable String bookingId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.requestRefund(bookingId));
    }
}
