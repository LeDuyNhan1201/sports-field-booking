package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentMethod;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentStatus;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payments")
public class Payment extends AbstractEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;

        @Column(nullable = false, length = 50)
        @Enumerated(EnumType.STRING)
        PaymentMethod method;

        @Column(nullable = false)
        Double price;

        @Column(nullable = false, length = 20)
        @Enumerated(EnumType.STRING)
        PaymentStatus status;

        @OneToOne
        @JoinColumn(name = "booking_id", referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "fk_payments_bookings",
                        foreignKeyDefinition = "FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE ON UPDATE CASCADE"),
                nullable = false, updatable = false)
        @JsonManagedReference
        Booking booking;

}
