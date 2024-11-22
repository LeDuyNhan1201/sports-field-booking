package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

import lombok.experimental.SuperBuilder;
import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;

@Getter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Setter
@SuperBuilder
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

        @Column
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

        @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JsonManagedReference
        List<SportsField> sportsFields;
}
