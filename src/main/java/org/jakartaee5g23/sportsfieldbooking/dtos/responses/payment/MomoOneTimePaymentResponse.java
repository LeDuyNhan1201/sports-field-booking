package org.jakartaee5g23.sportsfieldbooking.dtos.responses.payment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MomoOneTimePaymentResponse {

    String partnerCode;

    String orderId;

    String requestId;

    int amount;

    long responseTime;

    String message;

    int resultCode;

    String payUrl;

    String deeplink;

    String qrCodeUrl;

}
