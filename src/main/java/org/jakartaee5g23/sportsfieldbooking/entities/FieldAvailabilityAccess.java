package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "field_availabilities_access")
public class FieldAvailabilityAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    Date endDate;

    @ManyToOne
    @JoinColumn(name = "sports_field_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_field_availabilities_access_sports_fields",
                    foreignKeyDefinition = "FOREIGN KEY (sports_field_id) REFERENCES sports_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonBackReference
    SportsField sportsField;
}
