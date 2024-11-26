package org.jakartaee5g23.sportsfieldbooking.services;

import jakarta.servlet.http.HttpServletRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.payment.MomoOneTimePaymentResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PaymentService {

    Payment findById(String id);

    VNPayResponse createVNPayPayment(long amount, String orderId, HttpServletRequest request);

    MomoOneTimePaymentResponse createMomoOneTimePayment(String referenceId, String orderId, String orderInfo, double amount, String lang);

    boolean verifyVNPayPayment(Map<String, String> params, String orderId, String secureHash);

    Payment create(double amount, String orderId);

}
