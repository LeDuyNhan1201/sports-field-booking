package org.jakartaee5g23.sportsfieldbooking.services;

import jakarta.servlet.http.HttpServletRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.PaymentResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PaymentService {

    Payment findById(String id);

    VNPayResponse createVNPayPayment(long amount, String orderId, HttpServletRequest request);

    boolean verifyVNPayPayment(Map<String, String> params, String orderId, String secureHash);

    PaymentResponse createPayment(double amount, String orderID);

}
