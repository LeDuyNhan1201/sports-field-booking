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

        // reviews
        @ManyToOne
        @JoinColumn(name = "review_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_notifications_reviews", foreignKeyDefinition = "FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = true)
        @JsonManagedReference
        Review review;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        NotificationType type;

        @Column(nullable = false)
        String message;

        @Column(nullable = false)
        @Builder.Default
        @JoinColumn(name = "is_read")
        boolean isRead = false;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "sports_field_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_notifications_sports_fields", foreignKeyDefinition = "FOREIGN KEY (sports_field_id) REFERENCES sports_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = true)
        @JsonManagedReference
        SportsField sportField;
}