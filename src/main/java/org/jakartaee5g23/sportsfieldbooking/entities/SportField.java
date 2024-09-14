package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sport_fields")
public class SportField extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false, length = 100)
    String location;

    @Column(name = "price_per_hour", nullable = false)
    Integer pricePerHour;

    Integer opacity;

    @Column(name = "opening_time", nullable = false)
    Date openingTime;

    Date closingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    SportFieldStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_user_roles_categories",
                    foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false)
    @JsonManagedReference
    Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_sport_fields_users",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false)
    @JsonManagedReference
    User user;

    @OneToMany(mappedBy = "sportField", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    List<Order> orders;

    @OneToMany(mappedBy = "sportField", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    List<Review> reviews;

}
