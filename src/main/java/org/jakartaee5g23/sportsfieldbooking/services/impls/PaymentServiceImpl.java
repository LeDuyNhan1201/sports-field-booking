package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.configurations.VNPayConfiguration;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.PaymentResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentMethod;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.order.OrderErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.order.OrderException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.payment.PaymentErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.payment.PaymentException;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.PaymentRepository;
import org.jakartaee5g23.sportsfieldbooking.services.PaymentService;
import org.jakartaee5g23.sportsfieldbooking.vnpay.Utils.VNPayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    VNPayConfiguration vnPayConfiguration;

    RestTemplate restTemplate;

    OrderRepository orderRepository;

    PaymentRepository paymentRepository;

    @Override
    public boolean isVerifyPayment(PaymentMethod method, Map<String, String> params) {
        switch (method) {
            case VN_PAY:
                String orderId = params.get("vnp_TxnRef");
                String secureHash = params.get("vnp_SecureHash");
                return verifyVNPayPayment(params, orderId, secureHash);
            case CASH:
                // Add logic for CASH payment verification if needed
                return true;
            case CREDIT_CARD:
                // Add logic for CREDIT_CARD payment verification if needed
                return false;
            case DEBIT_CARD:
                // Add logic for DEBIT_CARD payment verification if needed
                return false;
            case PAYPAL:
                // Add logic for PAYPAL payment verification if needed
                return false;
            default:
                return false;
        }
    }

    @Override
    public VNPayResponse createVNPayPayment(long amount, String orderID, HttpServletRequest request) {
        long vnpAmount = amount * 100L;

        Map<String, String> vnpParamsMap = vnPayConfiguration.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(vnpAmount));

        vnpParamsMap.put("vnp_TxnRef", orderID);

        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(request));

        String queryUrl = VNPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtils.hmacSHA512(vnPayConfiguration.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        String paymentUrl = vnPayConfiguration.getVnp_PayUrl() + "?" + queryUrl;
        return new VNPayResponse("ok", "success", paymentUrl);
    }

    @Override
    public boolean verifyVNPayPayment(Map<String, String> params, String orderId, String secureHash) {
        // Filter out the vnp_SecureHash parameter
        Map<String, String> filteredParams = params.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("vnp_SecureHash"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Recalculate the hash
        String calculatedHash = VNPayUtils.hmacSHA512(vnPayConfiguration.getSecretKey(), VNPayUtils.getPaymentURL(filteredParams, false));

        // Compare secure hashes
        if (!calculatedHash.equals(secureHash)) {
            return false;
        }

        // Get payment status from params
        String paymentStatus = params.get("vnp_ResponseCode");
        Double price = Double.parseDouble(params.get("vnp_Amount")) / 100;

        // Fetch the order using the orderId
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BookingException(BookingErrorCode.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        PaymentStatus paymentStatusEnum = "00".equals(paymentStatus) ? PaymentStatus.COMPLETED : PaymentStatus.PENDING;

        Payment payment = Payment.builder()
                .method(PaymentMethod.VN_PAY)
                .price(price)
                .order(order)
                .status(paymentStatusEnum)
                .build();

        paymentRepository.save(payment);

        paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new BookingException(BookingErrorCode.PAYMENT_NOT_FOUND, HttpStatus.NOT_FOUND));

        return "00".equals(paymentStatus);
    }

    @Override
    public PaymentResponse createPayment(double amount, String orderID) {
        Order order = orderRepository.findById(orderID).orElseThrow(() -> new BookingException(BookingErrorCode.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (amount <= 0) throw new PaymentException(PaymentErrorCode.PAYMENT_AMOUNT_INVALID, HttpStatus.BAD_REQUEST);
        if (order == null) throw new PaymentException(PaymentErrorCode.PAYMENT_ORDER_NOT_FOUND, HttpStatus.NOT_FOUND);
        if (order.getStatus() != OrderStatus.PENDING) throw new OrderException(OrderErrorCode.ORDER_CHECK_PENDING, HttpStatus.BAD_REQUEST);

        Payment payment = Payment.builder()
                .price(amount)
                .order(order)
                .method(PaymentMethod.CASH)
                .status(PaymentStatus.PENDING)
                .build();

        paymentRepository.save(payment);

        paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new BookingException(BookingErrorCode.PAYMENT_NOT_FOUND, HttpStatus.NOT_FOUND));

        return new PaymentResponse(getLocalizedMessage("payment_success"), HttpStatus.OK);
    }


}
