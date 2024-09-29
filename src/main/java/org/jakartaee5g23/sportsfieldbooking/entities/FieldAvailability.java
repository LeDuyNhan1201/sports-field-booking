package org.jakartaee5g23.sportsfieldbooking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "field_availability")
public class FieldAvailability extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "yard_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_field_availability_sport_fields", foreignKeyDefinition = "FOREIGN KEY (yard_id) REFERENCES sport_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"), nullable = false)
    @JsonBackReference
    SportField sportField;

    @Temporal(TemporalType.DATE)
    @Column(name = "available_date", nullable = false)
    Date availableDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", nullable = false)
    Date endTime;

    @Column(name = "is_available", nullable = false)
    Boolean isAvailable;
}
