package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sports_fields")
public class SportsField extends AbstractEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;

        @Column(nullable = false, length = 100)
        String name;

        @Column(nullable = false, length = 100)
        String location;

        @Column(name = "price_per_hour", nullable = false)
        Double pricePerHour;

        Integer opacity;

        @Column(name = "opening_time", nullable = false)
        Date openingTime;

        Date closingTime;

        Double rating;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        SportsFieldStatus status;

        @ManyToOne
        @JoinColumn(name = "category_id", referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "fk_sports_fields_categories",
                        foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE"),
                nullable = false)
        @JsonManagedReference
        Category category;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "fk_sports_fields_users",
                        foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"),
                nullable = false)
        @JsonManagedReference
        User user;

        @OneToMany(mappedBy = "sportsField", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonBackReference
        List<FieldAvailability> fieldAvailabilities;

        @OneToMany(mappedBy = "sportsField", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JsonManagedReference
        List<FileMetadata> images;

        @OneToMany(mappedBy = "sportsField", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonBackReference
        List<Review> reviews;

}
