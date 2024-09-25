package org.jakartaee5g23.sportsfieldbooking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "promotions")
public class Promotion {

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

    @ManyToOne
    @JoinColumn(name = "yard_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_promotions_sport_fields", foreignKeyDefinition = "FOREIGN KEY (yard_id) REFERENCES sport_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false)
    SportField sportField;
}
