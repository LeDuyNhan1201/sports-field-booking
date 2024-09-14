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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String comment;

    @Column(columnDefinition = "double default 0")
    Double rating;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_reviews_users",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonManagedReference
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_field_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_reviews_sport_fields",
                    foreignKeyDefinition = "FOREIGN KEY (sport_field_id) REFERENCES sport_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonBackReference
    SportField sportField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_review_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_parent_review",
                    foreignKeyDefinition = "FOREIGN KEY (parent_review_id) REFERENCES reviews(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            updatable = false)
    @JsonBackReference
    Review parentReview;

    @OneToMany(mappedBy = "parentReview", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Review> replies;

}
