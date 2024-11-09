package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.configs.VNPayConfig;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentMethod;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.payment.PaymentErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.payment.PaymentException;
import org.jakartaee5g23.sportsfieldbooking.repositories.PaymentRepository;
import org.jakartaee5g23.sportsfieldbooking.services.OrderService;
import org.jakartaee5g23.sportsfieldbooking.services.PaymentService;
import org.jakartaee5g23.sportsfieldbooking.vnpay.Utils.VNPayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    VNPayConfig vnPayConfig;

    OrderService orderService;

    PaymentRepository paymentRepository;

    @Override
    public Payment findById(String id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Payment"));
    }

    @Override
    public VNPayResponse createVNPayPayment(long amount, String orderId, HttpServletRequest request) {
        long vnpAmount = amount * 100L;

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(vnpAmount));

        vnpParamsMap.put("vnp_TxnRef", orderId);

        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(request));

        String queryUrl = VNPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return new VNPayResponse("ok", getLocalizedMessage("payment_success"), paymentUrl);
    }

    @Override
    public boolean verifyVNPayPayment(Map<String, String> params, String orderId, String secureHash) {
        // Filter out the vnp_SecureHash parameter
        Map<String, String> filteredParams = params.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("vnp_SecureHash"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Recalculate the hash
        String calculatedHash = VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), VNPayUtils.getPaymentURL(filteredParams, false));

        // Compare secure hashes
        if (!calculatedHash.equals(secureHash)) {
            return false;
        }

        // Get payment status from params
        String paymentStatus = params.get("vnp_ResponseCode");
        Double price = Double.parseDouble(params.get("vnp_Amount")) / 100;

        // Fetch the order using the orderId
        Booking booking = orderService.findById(orderId);

        PaymentStatus paymentStatusEnum = "00".equals(paymentStatus) ? PaymentStatus.COMPLETED : PaymentStatus.PENDING;

        Payment payment = Payment.builder()
                .method(PaymentMethod.VN_PAY)
                .price(price)
                .booking(booking)
                .status(paymentStatusEnum)
                .createdBy(booking.getUser().getId())
                .build();

        paymentRepository.save(payment);

        paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Payment"));

        return "00".equals(paymentStatus);
    }

    @Override
    public Payment create(double amount, String orderId) {
        Booking booking = orderService.findById(orderId);

        if (amount <= 0) throw new PaymentException(PaymentErrorCode.PAYMENT_AMOUNT_INVALID, HttpStatus.BAD_REQUEST);
        if (booking == null) throw new PaymentException(PaymentErrorCode.PAYMENT_ORDER_NOT_FOUND, HttpStatus.NOT_FOUND);
        if (booking.getStatus() != BookingStatus.PENDING) throw new BookingException(BookingErrorCode.BOOKING_CHECK_PENDING, HttpStatus.BAD_REQUEST);

        Payment payment = Payment.builder()
                .price(amount)
                .booking(booking)
                .method(PaymentMethod.CASH)
                .status(PaymentStatus.PENDING)
                .createdBy(booking.getUser().getId())
                .build();

        return paymentRepository.save(payment);
    }


}
