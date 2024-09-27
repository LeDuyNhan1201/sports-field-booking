package org.jakartaee5g23.sportsfieldbooking.services;

import jakarta.servlet.http.HttpServletRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.CommonResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.PaymentResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.VNPayResponse;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PaymentService {
    VNPayResponse createVNPayPayment(long amount, String orderID,HttpServletRequest request);
    boolean verifyVNPayPayment(Map<String, String> params, String orderId, String secureHash);

    PaymentResponse createPayment(double amount, String orderID);

}
