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

    @GetMapping("/test")
    public String getTestPage() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Thanh Toán</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 20px;\n" +
                "            padding: 0;\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            border: 1px solid #ccc;\n" +
                "            border-radius: 8px;\n" +
                "        }\n" +
                "\n" +
                "        .form-group {\n" +
                "            margin-bottom: 15px;\n" +
                "        }\n" +
                "\n" +
                "        label {\n" +
                "            display: block;\n" +
                "            margin-bottom: 5px;\n" +
                "        }\n" +
                "\n" +
                "        input[type=\"text\"],\n" +
                "        select {\n" +
                "            width: 100%;\n" +
                "            padding: 8px;\n" +
                "            box-sizing: border-box;\n" +
                "            border: 1px solid #ddd;\n" +
                "            border-radius: 4px;\n" +
                "        }\n" +
                "\n" +
                "        .button-group {\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "\n" +
                "        button {\n" +
                "            padding: 10px 15px;\n" +
                "            border: none;\n" +
                "            border-radius: 4px;\n" +
                "            color: #fff;\n" +
                "            cursor: pointer;\n" +
                "        }\n" +
                "\n" +
                "        .submit-btn {\n" +
                "            background-color: #4CAF50;\n" +
                "        }\n" +
                "\n" +
                "        .cancel-btn {\n" +
                "            background-color: #f44336;\n" +
                "            margin-left: 10px;\n" +
                "        }\n" +
                "\n" +
                "        #qr-code {\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <h2>Thanh Toán</h2>\n" +
                "    <form id=\"payment-form\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"payment-method\">Phương thức thanh toán:</label>\n" +
                "            <select id=\"payment-method\" name=\"paymentMethod\" required>\n" +
                "                <option value=\"\" disabled selected>Chọn phương thức</option>\n" +
                "                <option value=\"cash\">Tiền mặt</option>\n" +
                "                <option value=\"transfer\">Chuyển khoản</option>\n" +
                "            </select>\n" +
                "        </div>\n" +
                "        <div id=\"qr-code\" style=\"display: none;\">\n" +
                "            <img id=\"qr-code-img\" alt=\"QR Code\">\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"cash\">Số tiền:</label>\n" +
                "            <input type=\"text\" id=\"cash\" name=\"cash\" readonly value=\"500000\">\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"invoice\">Hóa đơn:</label>\n" +
                "            <input type=\"text\" id=\"invoice\" name=\"invoice\">\n" +
                "        </div>\n" +
                "        <div class=\"button-group\">\n" +
                "            <button type=\"submit\" class=\"submit-btn\">Submit</button>\n" +
                "            <button type=\"button\" class=\"cancel-btn\" onclick=\"window.location.reload()\">Cancel</button>\n"
                +
                "        </div>\n" +
                "    </form>\n" +
                "</div>\n" +
                "<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\"></script>\n" +
                "<script>\n" +
                "    document.getElementById('payment-form').addEventListener('submit', async function (event) {\n" +
                "        event.preventDefault();\n" +
                "\n" +
                "        const paymentMethod = document.getElementById('payment-method').value;\n" +
                "        const invoice = document.getElementById('invoice').value;\n" +
                "        const amount = document.getElementById('cash').value;\n" +
                "\n" +
                "\n" +
                "        if (paymentMethod === 'transfer') {\n" +
                "            const response = await fetch('/sports-field-booking/api/v1/payment/createVNPayPayment', {\n"
                +
                "                method: 'POST',\n" +
                "                headers: {\n" +
                "                    'Content-Type': 'application/json'\n" +
                "                },\n" +
                "                body: JSON.stringify({\n" +
                "                    amount: amount,\n" +
                "                    bankCode: \"NCB\",\n" +
                "                    invoice: invoice\n" +
                "                })\n" +
                "            });\n" +
                "\n" +
                "            if (response.ok) {\n" +
                "                const data = await response.json();\n" +
                "\n" +
                "                window.location.href = data.paymentUrl;\n" +
                "            } else {\n" +
                "                console.error('Failed to create payment');\n" +
                "            }\n" +
                "        }else if(paymentMethod === 'cash') {\n" +
                "            const response = await fetch('/sports-field-booking/api/v1/payment/createPayment', {\n" +
                "                method: 'POST',\n" +
                "                headers: {\n" +
                "                    'Content-Type': 'application/json'\n" +
                "                },\n" +
                "                body: JSON.stringify({\n" +
                "                    amount: amount,\n" +
                "                    invoice: invoice\n" +
                "                })\n" +
                "            });\n" +
                "\n" +
                "            if (response.ok) {\n" +
                "                alert(\"ok\");\n" +
                "            } else {\n" +
                "                console.error('Failed to create payment');\n" +
                "            }\n" +
                "        }\n" +
                "    });\n" +
                "\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";
    }
    // for UI tests
    // UI link: http://localhost:8888/sports-field-booking/api/v1/booking/test
    // Card number: 9704198526191432198
    // Owner name: NGUYEN VAN A
    // Date: 07/15
    // @Operation(summary = "Create VNPay Payment", description = "Create VNPay
    // Payment")
    // @PostMapping("/createVNPayPayment")
    // public ResponseEntity<VNPayResponse> createVNPayPayment(@RequestBody
    // Map<String, Object> requestBody, HttpServletRequest request) {
    // String amountString = (String) requestBody.get("amount");
    // double amountDouble = Double.parseDouble(amountString);
    // long amount = (long) amountDouble;
    // String bankCode = (String) requestBody.get("bankCode");
    // String orderID = (String) requestBody.get("invoice");
    //
    // VNPayResponse vnPayResponse = paymentService.createVNPayPayment(amount,
    // bankCode, orderID, request);
    //
    // return ResponseEntity.ok(vnPayResponse);
    // }

    @Operation(summary = "Create VNPay Payment", description = "Create VNPay Payment")
    @PostMapping("/createVNPayPayment")
    public ResponseEntity<VNPayResponse> createVNPayPayment(@RequestBody @Valid VNPayRequest payRequest,
            HttpServletRequest request) {
        long amount = (long) payRequest.amount();
        String orderID = payRequest.orderID();

        VNPayResponse vnPayResponse = paymentService.createVNPayPayment(amount, "NCB", orderID, request);

        return ResponseEntity.ok(vnPayResponse);
    }

    // for UI tests
    // @Operation(summary = "Create Payment", description = "Create Payment")
    // @PostMapping("/createPayment")
    // public ResponseEntity<PaymentResponse> createPayment(@RequestBody Map<String,
    // Object> requestBody) {
    // String amountString = (String) requestBody.get("amount");
    // double amount = Double.parseDouble(amountString);
    // String orderID = (String) requestBody.get("invoice");
    //
    // PaymentResponse paymentResponse = paymentService.createPayment(amount,
    // orderID);
    //
    // return ResponseEntity.ok(paymentResponse);
    // }

    @Operation(summary = "Create Payment", description = "Create Payment")
    @PostMapping("/createPayment")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest request) {
        double amount = (double) request.amount();
        String orderID = request.orderID();

        PaymentResponse paymentResponse = paymentService.createPayment(amount, orderID);

        return ResponseEntity.ok(paymentResponse);
    }

    @Operation(summary = "VNPay Callback", description = "Handle VNPay payment callback")
    @GetMapping("/vnPayCallback")
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
    @GetMapping("/sport-field-paymentInfo")
    public String getPaymentInfo(@RequestBody @Valid String orderID, Model model) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new BookingException(BookingErrorCode.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        Double price = 0.0;
        List<SportField> sportFieldList = sportFieldRepository.findAll();
        for (SportField field : sportFieldList) {
            if (order.getSportField().getId().equals(field.getId())) {
                price = field.getPricePerHour() * order.getBookingHours();
            }
        }
        model.addAttribute("price", price);
        model.addAttribute("orderID", orderID);
        return "sport-field-paymentInfo";
    }
}
