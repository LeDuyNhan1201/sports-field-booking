package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.booking.BookingItemRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingItemResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.mappers.BookingItemMapper;
import org.jakartaee5g23.sportsfieldbooking.services.BookingItemsService;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.jakartaee5g23.sportsfieldbooking.services.FieldAvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("${api.prefix}/booking-items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Booking Items APIs")
public class BookingItemController {
    BookingItemsService bookingItemsService;
    BookingService bookingService;
    FieldAvailabilityService fieldAvailabilityService;
    BookingItemMapper bookingItemMapper = BookingItemMapper.INSTANCE;

    @Operation(summary = "Add Booking Item", description = "Create booking item with booking ID and field availability ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<BookingItemResponse> createBookingItem(@RequestBody BookingItemRequest bookingItemRequest) throws ParseException{
        Booking booking = bookingService.findById(bookingItemRequest.orderId());
        FieldAvailability fieldAvailability = fieldAvailabilityService.findById(bookingItemRequest.fieldAvailabilityId());

        BookingItem bookingItem = BookingItem.builder()
                .booking(booking)
                .fieldAvailability(fieldAvailability)
                .availableDate(bookingItemRequest.availableDate())
                .startTime(bookingItemRequest.startTime())
                .endTime(bookingItemRequest.endTime())
                .price(bookingItemRequest.price())
                .build();

        fieldAvailability.setIsAvailable(false);
        fieldAvailabilityService.update(fieldAvailability);

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookingItemMapper.toBookingItemResponse(bookingItemsService.create(bookingItem)));
    }
}
