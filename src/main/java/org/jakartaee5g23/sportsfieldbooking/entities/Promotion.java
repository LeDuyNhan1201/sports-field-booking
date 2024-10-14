package org.jakartaee5g23.sportsfieldbooking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "promotions")
public class Promotion extends AbstractEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id;

        @Column(nullable = false, length = 100)
        String name;

        @Column(length = 255)
        String description;

        @Column(name = "discount_percentage", nullable = false)
        Double discountPercentage;

        @Temporal(TemporalType.DATE)
        @Column(name = "start_date", nullable = false)
        Date startDate;

        @Temporal(TemporalType.DATE)
        @Column(name = "end_date", nullable = false)
        Date endDate;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        PromotionStatus status;
}
