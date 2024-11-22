package org.jakartaee5g23.sportsfieldbooking.dtos.responses.promotion;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponse {
    String id;
    String name;
    String description;
    Double discountPercentage;
    Date startDate;
    Date endDate;
    PromotionStatus status;
    String createdBy;

}
