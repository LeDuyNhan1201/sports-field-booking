package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;
import org.jakartaee5g23.sportsfieldbooking.mappers.BookingMapper;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus.*;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.getUserIdFromContext;
import static org.springframework.http.HttpStatus.OK;

import java.util.*;

@RestController
@RequestMapping("${api.prefix}/booking")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Booking APIs")
public class BookingController {

        BookingMapper bookingMapper = BookingMapper.INSTANCE;

        BookingService bookingService;

        UserService userService;

        @Operation(summary = "Get booking by id", description = "Get booking by id", security = @SecurityRequirement(name = "bearerAuth"))
        @GetMapping("/{id}")
        public ResponseEntity<BookingResponse> findById(@PathVariable String id) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(bookingMapper.toBookingResponse(bookingService.findById(id)));
        }

        @Operation(summary = "Confirm booking", description = "Save booking's order", security = @SecurityRequirement(name = "bearerAuth"))
        @PostMapping("/{fieldAvailabilityId}")
        public ResponseEntity<BookingResponse> booking(@PathVariable String fieldAvailabilityId) {
                User current = userService.findById(getUserIdFromContext());

                Booking booking = Booking.builder()
                                .user(current)
                                .build();

                // if (fieldAvailabilityService.isAlreadyOrdered(booking))
                // throw new BookingException(BookingErrorCode.FIELD_AVAILABILITY_ORDERED,
                // HttpStatus.UNPROCESSABLE_ENTITY);

                return ResponseEntity.status(HttpStatus.OK)
                                .body(bookingMapper.toBookingResponse(bookingService.create(booking)));
        }

        @Operation(summary = "Cancel booking", description = "Cancel booking after ordered", security = @SecurityRequirement(name = "bearerAuth"))
        @DeleteMapping("/{id}")
        @PostAuthorize("returnObject.body.MUser.id == authentication.name")
        public ResponseEntity<BookingResponse> cancelBooking(@PathVariable String id) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(bookingMapper.toBookingResponse(bookingService.updateStatus(id, CANCELED)));
        }

        @Operation(summary = "Delete booking", description = "Delete booking after ordered", security = @SecurityRequirement(name = "bearerAuth"))
        @DeleteMapping("/delete-booking/{id}")
        public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
                bookingService.deleteBooking(id);
                return ResponseEntity.status(HttpStatus.OK).build();
        }

        @Operation(summary = "Reschedule booking", description = "Reschedule an existing booking", security = @SecurityRequirement(name = "bearerAuth"))
        @PutMapping("/reschedule/{id}")
        @PostAuthorize("returnObject.body.MUser.id == authentication.name")
        public ResponseEntity<BookingResponse> reschedule(@PathVariable String id) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(bookingMapper.toBookingResponse(bookingService.updateStatus(id, RESCHEDULED)));
        }

        @Operation(summary = "Request refund", description = "Request a refund for a booking", security = @SecurityRequirement(name = "bearerAuth"))
        @PutMapping("/refund/{id}")
        @PostAuthorize("returnObject.body.MUser.id == authentication.name")
        public ResponseEntity<BookingResponse> refund(@PathVariable String id) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(bookingMapper
                                                .toBookingResponse(bookingService.updateStatus(id, REFUND_REQUESTED)));
        }

        @Operation(summary = "Approve bookings", description = "User confirm or reject booking by end user", security = @SecurityRequirement(name = "bearerAuth"))
        @PutMapping("/approve/{id}")
        public ResponseEntity<BookingResponse> approve(@PathVariable String id) {
                return ResponseEntity.status(OK)
                                .body(bookingMapper.toBookingResponse(bookingService.updateStatus(id, ACCEPTED)));
        }

        @Operation(summary = "Get booking list", description = "Get booking list", security = @SecurityRequirement(name = "bearerAuth"))
        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PaginateResponse<BookingResponse>> findAll(
                        @RequestParam(defaultValue = "0") String offset,
                        @RequestParam(defaultValue = "100") String limit) {
                Page<Booking> bookings = bookingService.findAll(Integer.parseInt(offset), Integer.parseInt(limit));
                return ResponseEntity.status(HttpStatus.OK)
                                .body(PaginateResponse.<BookingResponse>builder()
                                                .items(bookings.stream().map(bookingMapper::toBookingResponse).toList())
                                                .pagination(new Pagination(Integer.parseInt(offset),
                                                                Integer.parseInt(limit),
                                                                bookings.getTotalElements()))
                                                .build());
        }

        @Operation(summary = "Get order by status", description = "Find order by status when user want filter", security = @SecurityRequirement(name = "bearerAuth"))
        @GetMapping("/get-order-by-status")
        @PreAuthorize("hasRole('FIELD_OWNER')")
        public ResponseEntity<PaginateResponse<BookingResponse>> findAllByStatus(@RequestParam BookingStatus status,
                        @RequestParam(defaultValue = "0") String offset,
                        @RequestParam(defaultValue = "100") String limit) {
                Page<Booking> bookings = bookingService.findAllByStatus(status, Integer.parseInt(offset),
                                Integer.parseInt(limit));
                return ResponseEntity.status(HttpStatus.OK)
                                .body(PaginateResponse.<BookingResponse>builder()
                                                .items(bookings.stream().map(bookingMapper::toBookingResponse).toList())
                                                .pagination(new Pagination(Integer.parseInt(offset),
                                                                Integer.parseInt(limit),
                                                                bookings.getTotalElements()))
                                                .build());
        }

        @Operation(summary = "View booking history", description = "Get list of past bookings", security = @SecurityRequirement(name = "bearerAuth"))
        @GetMapping("/my-bookings")
        public ResponseEntity<PaginateResponse<BookingResponse>> getHistory(
                        @RequestParam(defaultValue = "0") String offset,
                        @RequestParam(defaultValue = "100") String limit) {
                User current = userService.findById(getUserIdFromContext());
                List<BookingStatus> statuses = Arrays.asList(BookingStatus.ACCEPTED, BookingStatus.REJECTED,
                                BookingStatus.CANCELED);
                Page<Booking> bookings = bookingService.findBookingHistoryByUser(current, statuses,
                                Integer.parseInt(offset),
                                Integer.parseInt(limit));
                return ResponseEntity.status(HttpStatus.OK)
                                .body(PaginateResponse.<BookingResponse>builder()
                                                .items(bookings.stream().map(bookingMapper::toBookingResponse).toList())
                                                .pagination(new Pagination(Integer.parseInt(offset),
                                                                Integer.parseInt(limit),
                                                                bookings.getTotalElements()))
                                                .build());
        }

        // @Operation(summary = "View upcoming bookings", description = "Get list of
        // upcoming bookings", security = @SecurityRequirement(name = "bearerAuth"))
        // @GetMapping("/upcoming/{userId}")
        // public ResponseEntity<PaginateResponse<BookingResponse>>
        // getUpcomingBookings(@PathVariable String userId,
        // @RequestParam(defaultValue = "0") String offset,
        // @RequestParam(defaultValue = "100") String limit) {
        // Page<Booking> bookings = bookingService.getUpcomingBookings(userId,
        // Integer.parseInt(offset),
        // Integer.parseInt(limit));
        // return ResponseEntity.status(HttpStatus.OK)
        // .body(PaginateResponse.<BookingResponse>builder()
        // .items(bookings.stream().map(bookingMapper::toBookingResponse).toList())
        // .pagination(new Pagination(Integer.parseInt(offset),
        // Integer.parseInt(limit),
        // bookings.getTotalElements()))
        // .build());
        // }

        @Operation(summary = "View my upcoming bookings", description = "Get list of my upcoming bookings", security = @SecurityRequirement(name = "bearerAuth"))
        @GetMapping("/my-upcoming")
        public ResponseEntity<PaginateResponse<BookingResponse>> getUpcomingBookings(
                        @RequestParam(defaultValue = "0") String offset,
                        @RequestParam(defaultValue = "100") String limit) {
                User current = userService.findById(getUserIdFromContext());
                List<BookingStatus> statuses = Collections.singletonList(BookingStatus.PENDING);
                Page<Booking> bookings = bookingService.findBookingHistoryByUser(current, statuses,
                                Integer.parseInt(offset), Integer.parseInt(limit));
                return ResponseEntity.status(HttpStatus.OK)
                                .body(PaginateResponse.<BookingResponse>builder()
                                                .items(bookings.stream().map(bookingMapper::toBookingResponse).toList())
                                                .pagination(new Pagination(Integer.parseInt(offset),
                                                                Integer.parseInt(limit), bookings.getTotalElements()))
                                                .build());
        }

        @PostMapping("/{id}/cancel")
        public ResponseEntity<Booking> cancelBookingById(@PathVariable String id) {
                Booking cancelledBooking = bookingService.cancelBooking(id);
                return ResponseEntity.ok(cancelledBooking);
        }

        @GetMapping("/current-month")
        public ResponseEntity<List<BookingResponse>> getCurrentMonthBookings(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate) {
                List<Booking> bookings = bookingService.getBookingsForCurrentMonth(fromDate);
                return ResponseEntity.ok(bookings.stream().map(bookingMapper::toBookingResponse).toList());
        }

        @GetMapping("/previous-month")
        public ResponseEntity<List<BookingResponse>> getPreviousMonthBookings(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
                List<Booking> bookings = bookingService.getBookingsForPreviousMonth(toDate);
                return ResponseEntity.ok(bookings.stream().map(bookingMapper::toBookingResponse).toList());
        }

        @GetMapping("/current-week")
        public ResponseEntity<List<BookingResponse>> getCurrentWeekBookings() {
                List<Booking> bookings = bookingService.getBookingsForCurrentWeek();
                return ResponseEntity.ok(bookings.stream().map(bookingMapper::toBookingResponse).toList());
        }

        @GetMapping("/previous-week")
        public ResponseEntity<List<BookingResponse>> getPreviousWeekBookings() {
                List<Booking> bookings = bookingService.getBookingsForPreviousWeek();
                return ResponseEntity.ok(bookings.stream().map(bookingMapper::toBookingResponse).toList());
        }

        @GetMapping("/from-year")
        public ResponseEntity<List<BookingResponse>> getFromDateBookings(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate) {
                List<Booking> bookings = bookingService.getBookingsFromYear(fromDate);
                return ResponseEntity.ok(bookings.stream().map(bookingMapper::toBookingResponse).toList());
        }

        @GetMapping("/to-year")
        public ResponseEntity<List<BookingResponse>> getPreviousYearBookings(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
                List<Booking> bookings = bookingService.getBookingsToYear(toDate);
                return ResponseEntity.ok(bookings.stream().map(bookingMapper::toBookingResponse).toList());
        }

        @GetMapping("/search")
        public ResponseEntity<PaginateResponse<BookingResponse>> searchBookings(
                @RequestParam(required = false) String keyword,
                @RequestParam(required = false) BookingStatus status,
                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                @RequestParam(defaultValue = "0") String offset,
                @RequestParam(defaultValue = "100") String limit) {

                Page<Booking> bookings = bookingService.searchBookings(keyword, status, startDate, endDate, Integer.parseInt(offset), Integer.parseInt(limit));

                return ResponseEntity.ok(
                        PaginateResponse.<BookingResponse>builder()
                                .items(bookings.stream().map(BookingMapper.INSTANCE::toBookingResponse).toList())
                                .pagination(new Pagination(Integer.parseInt(offset), Integer.parseInt(limit), bookings.getTotalElements()))
                                .build());
        }

        @Operation(summary = "Update booking status", description = "Update the status of a booking by ID", security = @SecurityRequirement(name = "bearerAuth"))
        @PutMapping("/update-status/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<BookingResponse> updateBookingStatus(@PathVariable String id, @RequestParam BookingStatus newStatus) {
                Booking updatedBooking = bookingService.updateStatus(id, newStatus);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(bookingMapper.toBookingResponse(updatedBooking));
        }

        @GetMapping("/booking-status")
        public List<String> getBookingStatuses() {
                return bookingService.getBookingStatus();
        }
}
