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
import java.util.List;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.PaymentRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.VNPayRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.PaymentResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportFieldRepository;

@RestController
@RequestMapping("${api.prefix}/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Payment APIs")
public class PaymentController {
    PaymentService paymentService;
    OrderRepository orderRepository;
    SportFieldRepository sportFieldRepository;


    // Card number: 9704198526191432198
    // Owner name: NGUYEN VAN A
    // Date: 07/15
    // Bank code: Base on card's type in API card: https://sandbox.vnpayment.vn/apis/vnpay-demo
    @Operation(summary = "Create VNPay Payment", description = "Create VNPay Payment")
    @PostMapping("/vnpay")
    public ResponseEntity<VNPayResponse> createVNPayPayment(@RequestBody @Valid VNPayRequest payRequest,
                                                            HttpServletRequest request) {
        long amount = (long) payRequest.amount();
        String orderID = payRequest.orderID();

        VNPayResponse vnPayResponse = paymentService.createVNPayPayment(amount, orderID, request);

        return ResponseEntity.ok(vnPayResponse);
    }

    @Operation(summary = "Create Payment", description = "Create Payment")
    @PostMapping("/createPayment")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest request) {
        double amount = (double) request.amount();
        String orderID = request.orderID();

        PaymentResponse paymentResponse = paymentService.createPayment(amount, orderID);

        return ResponseEntity.ok(paymentResponse);
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

    @Operation(summary = "Get sport field price & orderID", description = "Get sport field price & orderID when user clicks on payment form", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/payment-info")
    public ResponseEntity<Map<String, Object>> getPaymentInfo(@RequestParam @Valid String orderID) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new BookingException(BookingErrorCode.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        Double price = 0.0;
        List<SportField> sportFieldList = sportFieldRepository.findAll();
        for (SportField field : sportFieldList) {
            if (order.getSportField().getId().equals(field.getId())) {
                int hours = (int) Duration.between((Temporal) order.getFieldAvailability().getStartTime(), (Temporal) order.getFieldAvailability().getEndTime()).toHours();
                price = (double) (field.getPricePerHour() * hours);
            }
        }
        return ResponseEntity.ok(Map.of("price", price, "orderID", orderID));
    }
}
