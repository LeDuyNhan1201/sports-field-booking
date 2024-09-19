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
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.PaymentRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.PaymentResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentMethod;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.jakartaee5g23.sportsfieldbooking.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
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


    @Operation(summary = "Make payment", description = "Make payment",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/making-payment")
    ResponseEntity<PaymentResponse> makePayment(@RequestBody @Valid PaymentRequest request) {
        PaymentMethod method = request.method();

        boolean isVerifyPayment = paymentService.isVerifyPayment(method, request.price());

        if (!isVerifyPayment) {
            return ResponseEntity.status(BAD_REQUEST).body(
                    new PaymentResponse(getLocalizedMessage("payment_failed")));
        }

        return ResponseEntity.status(OK).body(
                new PaymentResponse(getLocalizedMessage("payment_success"))
        );
    }

    @Operation(summary = "Confirm booking", description = "Save booking's order",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/confirm-booking")
    public ResponseEntity<BookingResponse> booking(@RequestBody @Valid BookingRequest request) {
        BookingResponse response = bookingService.getBookingConfirmation(request);

        return ResponseEntity.status(HttpStatus.OK).body(new BookingResponse(getLocalizedMessage("booking_success")));
    }


    @Operation(summary = "Cancel booking", description = "Cancel booking after ordered",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/cancel-booking")
    void cancelBooking(@RequestBody String orderID) {
        bookingService.cancelBooking(orderID);
    }

//    @Operation(summary = "Receive booking confirm", description = "Receive booking confirm",
//                security = @SecurityRequirement(name = "bearerAuth"))
//    @GetMapping("/receive-confirmation/{orderId}")
//    public ResponseEntity<BookingResponse> receiveConfirmation(@PathVariable String orderId) {
//        BookingResponse response = bookingService.getBookingConfirmation(orderId);
//
//        return ResponseEntity.status(OK).body(response);
//    }



}
