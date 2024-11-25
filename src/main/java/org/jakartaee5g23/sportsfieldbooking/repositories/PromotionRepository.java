package org.jakartaee5g23.sportsfieldbooking.repositories;

import java.util.Date;

import org.jakartaee5g23.sportsfieldbooking.entities.Promotion;
import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
        @Query("SELECT p FROM Promotion p " +
                        "WHERE (:keyword IS NULL OR " +
                        " p.id LIKE CONCAT('%', :keyword, '%') OR " +
                        " p.name LIKE CONCAT('%', :keyword, '%') OR " +
                        " p.description LIKE CONCAT('%', :keyword, '%')) " +
                        "AND (:status IS NULL OR p.status = :status) " +
                        "AND (:startDate IS NULL OR p.startDate >= :startDate) " +
                        "AND (:endDate IS NULL OR p.endDate <= :endDate)")
        Page<Promotion> searchPromotions(@Param("keyword") String keyword,
                        @Param("status") PromotionStatus status,
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate,
                        Pageable pageable);
}
