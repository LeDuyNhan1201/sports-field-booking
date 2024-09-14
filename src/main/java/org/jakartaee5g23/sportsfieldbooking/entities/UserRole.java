package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_role", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "role_id"})
})
public class UserRole extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_user_role_users",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonBackReference
    User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_user_role_roles",
                    foreignKeyDefinition = "FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonManagedReference
    Role role;

}
