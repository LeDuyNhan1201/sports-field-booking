package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "rating_point", nullable = false)
    Double rating_point;

    @ManyToOne
    @JoinColumn(name = "sports_field_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_ratings_sports_fields",
                    foreignKeyDefinition = "FOREIGN KEY (sports_field_id) REFERENCES sports_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonBackReference
    SportsField sportsField;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_ratings_users",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonManagedReference
    User user;

    @OneToOne
    @JoinColumn(name = "booking_item_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_ratings_booking_items",
                    foreignKeyDefinition = "FOREIGN KEY (booking_item_id) REFERENCES booking_items(id) ON DELETE CASCADE ON UPDATE CASCADE"))
    @JsonManagedReference
    BookingItem bookingItem;
}
