package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "bookings")
public class Booking extends AbstractEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;

        @Column(nullable = false, length = 20)
        @Enumerated(EnumType.STRING)
        BookingStatus status;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "fk_bookings_users",
                        foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"),
                nullable = false, updatable = false)
        @JsonManagedReference
        User user;

        @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonBackReference
        Payment payment;

        @OneToOne
        @JoinColumn(name = "field_availability_id", referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "fk_bookings_field_availabilities",
                        foreignKeyDefinition = "FOREIGN KEY (field_availability_id) REFERENCES field_availabilities(id) ON DELETE CASCADE ON UPDATE CASCADE"),
                nullable = false)
        @JsonManagedReference
        FieldAvailability fieldAvailability;

}
