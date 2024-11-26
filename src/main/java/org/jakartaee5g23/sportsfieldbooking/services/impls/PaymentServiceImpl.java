package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.configs.MomoConfig;
import org.jakartaee5g23.sportsfieldbooking.configs.VNPayConfig;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.payment.MomoOneTimePaymentRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.payment.MomoOneTimePaymentResponse;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.toHmacSHA256;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    VNPayConfig vnPayConfig;

    MomoConfig momoConfig;

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
    public MomoOneTimePaymentResponse createMomoOneTimePayment(String referenceId, String orderId, String orderInfo, double amount, String lang) {
        String data = generateRawHash(
                momoConfig.getAccessKey(),
                String.valueOf(amount),
                "",
                momoConfig.getRedirectUrl(),
                orderId,
                orderInfo,
                momoConfig.getPartnerCode(),
                momoConfig.getRedirectUrl(),
                referenceId,
                "captureWallet");

        MomoOneTimePaymentRequest momoRequest;
        try {
            momoRequest = MomoOneTimePaymentRequest.builder()
                    .partnerCode(momoConfig.getPartnerCode())
                    .requestId(referenceId)
                    .amount(String.valueOf(amount))
                    .orderId(orderId)
                    .orderInfo(orderInfo)
                    .redirectUrl(momoConfig.getRedirectUrl())
                    .ipnUrl(momoConfig.getRedirectUrl())
                    .requestType("captureWallet")
                    .lang(lang)
                    .signature(toHmacSHA256(data, momoConfig.getSecretKey()))
                    .items(List.of(
                            MomoOneTimePaymentRequest.Item.builder()
                                    .id("1")
                                    .name("Test")
                                    .quantity(1)
                                    .price(10000)
                                    .totalPrice(10000)
                                    .currency("VND")
                                    .build()
                    ))
                    .build();

        } catch (Exception e) {
            log.error("Error when generating signature for MoMo payment", e);
            throw new PaymentException(PaymentErrorCode.PAYMENT_MOMO_SIGNATURE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Momo request: {}", momoRequest.getSignature());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<MomoOneTimePaymentRequest> request = new HttpEntity<>(momoRequest, headers);

        String url = momoConfig.getPaymentUrl();
        ResponseEntity<MomoOneTimePaymentResponse> postResponse = restTemplate.exchange(url, HttpMethod.POST, request, MomoOneTimePaymentResponse.class);

        return postResponse.getBody();
    }

    public static String generateRawHash(String accessKey, String amount, String extraData, String ipnUrl,
                                         String orderId, String orderInfo, String partnerCode,
                                         String redirectUrl, String requestId, String requestType) {
        // Build data string in sorted order
        String data = "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=" + requestType;
        return data;
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
