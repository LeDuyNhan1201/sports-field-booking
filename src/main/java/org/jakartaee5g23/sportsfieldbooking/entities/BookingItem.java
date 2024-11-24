package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingItemStatus;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "booking_items")
public class BookingItem extends AbstractEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;

        @Temporal(TemporalType.DATE)
        @Column(name = "available_date", nullable = false)
        Date availableDate;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "start_time", nullable = false)
        Date startTime;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "end_time", nullable = false)
        Date endTime;

        @Column(name = "price", nullable = false)
        Double price;

        @ManyToOne
        @JoinColumn(name = "booking_id", referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "fk_booking_items_bookings",
                        foreignKeyDefinition = "FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE ON UPDATE CASCADE"),
                nullable = false, updatable = false)
        @JsonManagedReference
        Booking booking;

        @ManyToOne
        @JoinColumn(name = "sports_field_id", referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "fk_booking_items_sports_fields",
                        foreignKeyDefinition = "FOREIGN KEY (sports_field_id) REFERENCES sports_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"),
                nullable = false, updatable = false)
        @JsonManagedReference
        SportsField sportsField;

        @Enumerated(EnumType.STRING)
        @Column(name="status", nullable = false)
        BookingItemStatus status;

        @OneToOne(mappedBy = "bookingItem", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonBackReference
        Rating rating;

        @Override
        public String toString() {
                return "BookingItem{" +
                        "id='" + id + '\'' +
                        ", availableDate=" + availableDate +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", price=" + price +
                        ", booking=" + booking +
                        ", sportsField=" + sportsField +
                        ", status=" + status +
                        '}';
        }
}
