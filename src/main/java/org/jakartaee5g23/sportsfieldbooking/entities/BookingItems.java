package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "booking_items")
public class BookingItems extends AbstractEntity {

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

    @Column(name = "price_per_hour", nullable = false)
    Double pricePerHour;

    @ManyToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_booking_items_bookings",
                    foreignKeyDefinition = "FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonManagedReference
    Booking booking;

}
