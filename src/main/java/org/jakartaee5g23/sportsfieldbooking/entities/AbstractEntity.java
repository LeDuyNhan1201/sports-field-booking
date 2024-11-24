package org.jakartaee5g23.sportsfieldbooking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Calendar;
import java.util.Date;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.getCurrentUser;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.randomDate;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

    @CreatedBy
    @Column(name = "created_by")
    String createdBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "created_at")
    Date createdAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    String updatedBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    Date updatedAt;

    @Column(name = "is_deleted", nullable = false)
    Boolean isDeleted = false;

    @Column(name = "deleted_by")
    String deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    Date deletedAt;

    @Version
    @Column(name = "version")
    Long version;

    @PrePersist
    protected void onPrePersist() {
        if (this.isDeleted == null) {
            this.isDeleted = false;
        }

        if (this.createdBy == null) {
            this.createdBy = getCurrentUser();
        }

//        Date endDate = new Date();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(endDate);
//        calendar.add(Calendar.MONTH, -1);
//        Date startDate = calendar.getTime();
//
//        createdAt = randomDate(startDate, endDate);
    }

    @PreUpdate
    protected void onPreUpdate() {
        if (this.updatedBy == null) {
            this.updatedBy = getCurrentUser();
        }
    }

    // Hàm thực hiện xóa mềm
    public void softDelete() {
        this.isDeleted = true;
        this.deletedBy = getCurrentUser();
        this.deletedAt = new Date(); // Lấy thời gian hiện tại
    }

}
