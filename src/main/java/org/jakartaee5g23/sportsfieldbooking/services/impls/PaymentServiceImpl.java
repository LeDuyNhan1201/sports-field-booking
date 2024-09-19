package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jakartaee5g23.sportsfieldbooking.configurations.VNPayConfiguration;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentMethod;
import org.jakartaee5g23.sportsfieldbooking.services.PaymentService;
import org.jakartaee5g23.sportsfieldbooking.vnpay.Utils.VNPayUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final VNPayConfiguration vnPayConfiguration;

    @Override
    public boolean isVerifyPayment(PaymentMethod method, Double price) {
        switch (method) {
            case VN_PAY:
                return verifyVNPayPayment(price);
            case CASH:
                // Add logic for CASH payment verification if needed
                return false;
            case CREDIT_CARD:
                // Add logic for CREDIT_CARD payment verification if needed
                return false;
            case DEBIT_CARD:
                // Add logic for DEBIT_CARD payment verification if needed
                return false;
            case PAYPAL:
                // Add logic for PAYPAL payment verification if needed
                return false;
        }

        return false;
    }

    @Override
    public VNPayResponse createVNPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfiguration.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(request));
        // build query url
        String queryUrl = VNPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtils.hmacSHA512(vnPayConfiguration.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfiguration.getVnp_PayUrl() + "?" + queryUrl;
        return new VNPayResponse("ok", "success", paymentUrl);
    }

    @Override
    public boolean verifyVNPayPayment(double price) {

        return false;
    }

}
