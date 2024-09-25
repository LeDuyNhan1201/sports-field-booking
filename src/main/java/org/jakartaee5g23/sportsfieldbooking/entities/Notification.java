package org.jakartaee5g23.sportsfieldbooking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jakartaee5g23.sportsfieldbooking.enums.NotificationType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_notifications_users", foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false, updatable = false)
    @JsonBackReference
    User user;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_notifications_orders", foreignKeyDefinition = "FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = true)
    @JsonIgnore
    Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NotificationType type;

    @Column(nullable = false)
    String message;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    Date created;
}
