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
    PaymentMethod method;

    Double price;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_payments_orders",
                    foreignKeyDefinition = "FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonManagedReference
    Order order;

}
