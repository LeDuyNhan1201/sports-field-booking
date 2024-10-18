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

import java.time.Duration;
import java.time.temporal.Temporal;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.payment.PaymentRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.payment.VNPayRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.PaymentResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItems;
import org.jakartaee5g23.sportsfieldbooking.mappers.BookingItemsMapper;
import org.jakartaee5g23.sportsfieldbooking.mappers.PaymentMapper;
import org.jakartaee5g23.sportsfieldbooking.services.BookingItemsService;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.jakartaee5g23.sportsfieldbooking.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Payment APIs")
public class PaymentController {

    PaymentService paymentService;

    BookingService bookingService;

    BookingItemsService bookingItemsService;

    BookingItemsMapper bookingItemsMapper = BookingItemsMapper.INSTANCE;

    PaymentMapper paymentMapper = PaymentMapper.INSTANCE;

    // Card number: 9704198526191432198
    // Owner name: NGUYEN VAN A
    // Date: 07/15
    // Bank code: Base on card's type in API card: https://sandbox.vnpayment.vn/apis/vnpay-demo
    @Operation(summary = "Create VNPay Payment", description = "Create VNPay Payment")
    @PostMapping("/vnpay")
    public ResponseEntity<VNPayResponse> createVNPayPayment(@RequestBody @Valid VNPayRequest payRequest, HttpServletRequest request) {
        long amount = payRequest.amount();
        String orderId = payRequest.orderId();

        VNPayResponse vnPayResponse = paymentService.createVNPayPayment(amount, orderId, request);

        return ResponseEntity.ok(vnPayResponse);
    }

    @Operation(summary = "Create Payment", description = "Create Payment")
    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestBody @Valid PaymentRequest request) {
        double amount = request.amount();
        String orderId = request.orderId();
        return ResponseEntity.ok(paymentMapper.toPaymentResponse(paymentService.create(amount, orderId)));
    }

    @Operation(summary = "VNPay Callback", description = "Handle VNPay payment callback")
    @GetMapping("/vnpay/callback")
    public ResponseEntity<String> vnPayCallback(@RequestParam Map<String, String> params) {
        log.info("VNPay Callback received with params: {}", params);

        String orderId = params.get("vnp_TxnRef");
        String secureHash = params.get("vnp_SecureHash");

        boolean isVerified = paymentService.verifyVNPayPayment(params, orderId, secureHash);

        if (!isVerified) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid secure hash or payment failed");
        }

        return ResponseEntity.ok("Callback processed and payment verified");
    }

    @Operation(summary = "Get sport field price & bookingId", description = "Get sport field price & bookingId when user clicks on payment form", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/payment-info/{bookingId}")
    public ResponseEntity<Map<String, Object>> getPaymentInfo(@PathVariable String bookingItemsID) {
        BookingItems bookingItems = bookingItemsService.findById(bookingItemsID);
        int hours = (int) Duration.between((Temporal) bookingItems.getStartTime(), (Temporal) bookingItems.getEndTime()).toHours();
        double totalPrice = bookingItems.getPricePerHour() * hours;
        return ResponseEntity.ok(Map.of("totalPrice", totalPrice, "bookingId", bookingItemsMapper.toBookingItemsResponse(bookingItems)));
    }

}
