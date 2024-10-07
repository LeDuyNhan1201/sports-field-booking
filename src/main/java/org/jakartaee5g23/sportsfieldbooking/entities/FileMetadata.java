package org.jakartaee5g23.sportsfieldbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "file_metadata")
public class FileMetadata extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "object_key", nullable = false)
    String objectKey;

    @Column(name = "media_type", nullable = false)
    String contentType;

    @Column(nullable = false)
    long size;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_file_metadata_users",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonBackReference
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_field_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_file_metadata_sports_fields",
                    foreignKeyDefinition = "FOREIGN KEY (sports_field_id) REFERENCES sports_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            nullable = false, updatable = false)
    @JsonBackReference
    SportsField sportsField;

}
