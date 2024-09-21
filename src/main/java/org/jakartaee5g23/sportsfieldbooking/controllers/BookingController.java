package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.configurations.VNPayConfiguration;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.BookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.CancelBookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.PaymentRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.VNPayRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.CommonResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.PaymentResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.CancelBookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.PaymentRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.jakartaee5g23.sportsfieldbooking.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("${api.prefix}/booking")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Booking APIs")
public class BookingController {
    BookingService bookingService;

    OrderRepository orderRepository;

    PaymentService paymentService;

    SportFieldRepository sportFieldRepository;

    PaymentRepository paymentRepository;

    VNPayConfiguration vnPayConfiguration;

    @Operation(summary = "Get sport field price & orderID", description = "Get sport field price & orderID when user clicks on payment form",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/sport-field-paymentInfo")
    public String getPaymentInfo(@RequestBody @Valid String orderID, Model model) {
        Order order = orderRepository.findById(orderID).orElseThrow(() -> new BookingException(BookingErrorCode.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        Double price = 0.0;
        List<SportField> sportFieldList = sportFieldRepository.findAll();
        for (SportField field : sportFieldList) {
            if(order.getSportField().getId().equals(field.getId())) {
                price = field.getPricePerHour() * order.getBookingHours();
            }
        }
        model.addAttribute("price", price);
        model.addAttribute("orderID", orderID);
        return "sport-field-paymentInfo";
    }

    @Operation(summary = "Cancel booking", description = "Cancel booking after ordered",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/cancel-booking")
    public ResponseEntity<CancelBookingResponse> cancelBooking(@RequestBody @Valid CancelBookingRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.cancelBooking(request));
    }

    @Operation(summary = "View upcoming bookings", description = "Get list of upcoming bookings", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/upcoming-bookings")
    public ResponseEntity<List<BookingResponse>> getUpcomingBookings() {
        List<BookingResponse> upcomingBookings = bookingService.getUpcomingBookings();
        return ResponseEntity.status(HttpStatus.OK).body(upcomingBookings);
    }
}
