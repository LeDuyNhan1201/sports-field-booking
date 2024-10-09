package org.jakartaee5g23.sportsfieldbooking.repositories;

import java.util.Date;

import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface SportFieldRepository extends JpaRepository<SportField, String> {
    @Query("SELECT sf FROM SportField sf " +
            "WHERE (:name IS NULL OR sf.name LIKE %:name%) " +
            "AND (:location IS NULL OR sf.location LIKE %:location%) " +
            "AND (:time IS NULL OR (sf.openingTime <= :time AND sf.closingTime >= :time)) " +
            "AND (:minPrice IS NULL OR sf.pricePerHour >= :minPrice) " +
            "AND (:maxPrice IS NULL OR sf.pricePerHour <= :maxPrice) " +
            "AND (:categoryId IS NULL OR sf.category.id = :categoryId)")
    Page<SportField> searchSportFields(
            @Param("name") String name,
            @Param("location") String location,
            @Param("time") Date time,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("categoryId") Integer categoryId,
            Pageable pageable);
}