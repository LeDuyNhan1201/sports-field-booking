package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;

        @Column(name = "start_time", nullable = false)
        Date startTime;

        @Column(name = "booking_hours", nullable = false)
        Double bookingHours;

        @Column(name = "end_time", nullable = false)
        Date endTime;

        @Column(nullable = false, length = 20)
        @Enumerated(EnumType.STRING)
        OrderStatus status;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_orders_users", foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false, updatable = false)
        @JsonManagedReference
        User user;

        @ManyToOne
        @JoinColumn(name = "sport_field_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_orders_sport_fields", foreignKeyDefinition = "FOREIGN KEY (sport_field_id) REFERENCES sport_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false, updatable = false)
        @JsonManagedReference
        SportField sportField;

        @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonBackReference
        Payment payment;

}
