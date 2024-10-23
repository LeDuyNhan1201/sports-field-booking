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

    @Column
    String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_file_metadata_users",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            updatable = false)
    @JsonBackReference
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_field_id_thumbnail", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_file_metadata_sports_fields",
                    foreignKeyDefinition = "FOREIGN KEY (sports_field_id_thumbnail) REFERENCES sports_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            updatable = false)
    @JsonBackReference
    SportsField sportsFieldThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_field_id_image", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_file_metadatas_sports_fields",
                    foreignKeyDefinition = "FOREIGN KEY (sports_field_id_image) REFERENCES sports_fields(id) ON DELETE CASCADE ON UPDATE CASCADE"),
            updatable = false)
    @JsonBackReference
    SportsField sportsFieldImage;

}
