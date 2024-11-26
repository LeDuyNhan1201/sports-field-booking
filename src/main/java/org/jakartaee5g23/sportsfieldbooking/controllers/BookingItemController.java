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
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingItemStatus;
import org.jakartaee5g23.sportsfieldbooking.mappers.BookingItemMapper;
import org.jakartaee5g23.sportsfieldbooking.services.BookingItemsService;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/booking-items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Booking Items APIs")
public class BookingItemController {
        BookingItemsService bookingItemsService;
        BookingService bookingService;
        SportsFieldService sportsFieldService;
        BookingItemMapper bookingItemMapper = BookingItemMapper.INSTANCE;

        @Operation(summary = "Add Booking Item", description = "Create booking item with booking ID and sports field ID", security = @SecurityRequirement(name = "bearerAuth"))
        @PostMapping
        public ResponseEntity<BookingItemResponse> createBookingItem(@RequestBody BookingItemRequest bookingItemRequest)
                        throws ParseException {
                Booking booking = bookingService.findById(bookingItemRequest.orderId());
                SportsField sportsField = sportsFieldService.findById(bookingItemRequest.sportFieldID());

                BookingItem bookingItem = BookingItem.builder()
                                .booking(booking)
                                .sportsField(sportsField)
                                .availableDate(bookingItemRequest.availableDate())
                                .startTime(bookingItemRequest.startTime())
                                .endTime(bookingItemRequest.endTime())
                                .price(bookingItemRequest.price())
                                .status(BookingItemStatus.PENDING)
                                .updatedAt(new Date())
                                .build();

                return ResponseEntity.status(HttpStatus.OK)
                                .body(bookingItemMapper.toBookingItemResponse(bookingItemsService.create(bookingItem)));
        }

        @Operation(summary = "Get Booking Items by Sports Field ID", description = "Retrieve all booking items associated with a specific sports field ID", security = @SecurityRequirement(name = "bearerAuth"))
        @GetMapping("/sports-field/{sportsFieldId}")
        public ResponseEntity<List<BookingItemResponse>> getBookingItemsBySportsFieldId(
                        @PathVariable String sportsFieldId) {
                List<BookingItem> bookingItems = bookingItemsService.findBySportsFieldId(sportsFieldId);

                List<BookingItemResponse> responses = bookingItems.stream()
                                .map(bookingItemMapper::toBookingItemResponse)
                                .toList();

                return ResponseEntity.status(HttpStatus.OK).body(responses);
        }

        @Operation(summary = "Get booking item list", description = "Get booking item list", security = @SecurityRequirement(name = "bearerAuth"))
        @GetMapping
        @PreAuthorize("hasRole('FIELD_OWNER')")
        public ResponseEntity<PaginateResponse<BookingItemResponse>> findAll(
                        @RequestParam(defaultValue = "0") String offset,
                        @RequestParam(defaultValue = "100") String limit) {
                Page<BookingItem> bookingItems = bookingItemsService.findAll(Integer.parseInt(offset),
                                Integer.parseInt(limit));
                return ResponseEntity.status(HttpStatus.OK)
                                .body(PaginateResponse.<BookingItemResponse>builder()
                                                .items(bookingItems.stream()
                                                                .map(bookingItemMapper::toBookingItemResponse).toList())
                                                .pagination(new Pagination(Integer.parseInt(offset),
                                                                Integer.parseInt(limit),
                                                                bookingItems.getTotalElements()))
                                                .build());
        }
}
