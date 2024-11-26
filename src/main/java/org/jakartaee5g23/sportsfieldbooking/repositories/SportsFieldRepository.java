package org.jakartaee5g23.sportsfieldbooking.repositories;

import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsFieldRepository extends JpaRepository<SportsField, String> {

        Page<SportsField> findByUser(User user, Pageable pageable);

        @Query("SELECT sf FROM SportsField sf WHERE " +
                "(sf.name LIKE %:text% " +
                "OR sf.location LIKE %:text% " +
                "OR sf.category.id = CAST(:text AS integer) OR :text = ' ' ) " +
                "AND (:userId = '0' OR sf.user.id = :userId) " +
                "AND (:categoryId = 0 OR sf.category.id = :categoryId)")
        Page<SportsField> searchSportsFields(@Param("userId") String userId,
                                             @Param("text") String text,
                                             @Param("categoryId") Integer categoryId,
                                             Pageable pageable);


        @Query("SELECT sf FROM SportsField sf JOIN sf.fieldAvailabilities fa WHERE " +
                        "(sf.category.id = CAST(:categoryId AS integer)) " +
                        "AND sf.location LIKE %:location% " +
                        "AND fa.price BETWEEN :minPrice AND :maxPrice " +
                        "GROUP BY sf.id")
        Page<SportsField> findSportsFieldsByCategoryLocationPrice(@Param("categoryId") String categoryId,
                        @Param("location") String location,
                        @Param("minPrice") double minPrice,
                        @Param("maxPrice") double maxPrice,
                        Pageable pageable);
}