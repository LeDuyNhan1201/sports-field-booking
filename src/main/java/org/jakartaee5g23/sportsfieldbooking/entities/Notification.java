package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.experimental.SuperBuilder;
import org.jakartaee5g23.sportsfieldbooking.enums.NotificationType;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "notifications")
public class Notification extends AbstractEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_notifications_users", foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false, updatable = false)
        @JsonBackReference
        User user;

        @ManyToOne
        @JoinColumn(name = "booking_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_notifications_bookings", foreignKeyDefinition = "FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE ON UPDATE CASCADE"))
        @JsonManagedReference
        Booking booking;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        NotificationType type;

        @Column(nullable = false)
        String message;

        @Column(nullable = false)
        @Builder.Default
        @JoinColumn(name = "is_read")
        boolean isRead = false;
}
