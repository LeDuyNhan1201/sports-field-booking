package org.jakartaee5g23.sportsfieldbooking.configs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class MomoConfig {

    @NonFinal
    @Value("${payment.momo.paymentUrl}")
    String paymentUrl;

    @NonFinal
    @Value("${payment.momo.redirectUrl}")
    String redirectUrl;

    @NonFinal
    @Value("${payment.momo.partnerCode}")
    String partnerCode;

    @NonFinal
    @Value("${payment.momo.accessKey}")
    String accessKey;

    @NonFinal
    @Value("${payment.momo.secretKey}")
    String secretKey;

}
