package org.jakartaee5g23.sportsfieldbooking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "statistic")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    Date date;

    @Column(name = "total_orders", nullable = false)
    Integer totalOrders;

    @Column(name = "total_payments", nullable = false)
    Integer totalPayments;

    @Column(name = "total_revenue", nullable = false)
    Integer totalRevenue;

    @Column(name = "active_users", nullable = false)
    Integer activeUsers;
}
