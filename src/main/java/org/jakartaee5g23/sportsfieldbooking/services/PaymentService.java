package org.jakartaee5g23.sportsfieldbooking.services;

import jakarta.servlet.http.HttpServletRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    boolean isVerifyPayment(PaymentMethod method, Double price);
    VNPayResponse createVNPayPayment(HttpServletRequest request);
    boolean verifyVNPayPayment(double price);
}
