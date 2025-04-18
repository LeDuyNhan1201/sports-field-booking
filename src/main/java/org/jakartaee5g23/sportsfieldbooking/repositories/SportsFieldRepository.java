package org.jakartaee5g23.sportsfieldbooking.repositories;

import io.micrometer.observation.annotation.Observed;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface SportsFieldRepository extends JpaSpecificationExecutor<SportsField>, JpaRepository<SportsField, String> {

        Page<SportsField> findByUser(User user, Pageable pageable);

        @Query("SELECT sf FROM SportsField sf JOIN sf.fieldAvailabilities fa WHERE " +
                "(sf.name LIKE %:text% " +
                "OR sf.location LIKE %:text% " +
                "OR fa.price = CAST(:text AS double) " +
                "OR sf.category.id = CAST(:text AS integer) OR :text = ' ' ) " +
                "AND (:userId = '0' OR sf.user.id = :userId) " +
                "AND (:categoryId = 0 OR sf.category.id = :categoryId) " +
                "AND fa.price BETWEEN :minPrice AND :maxPrice " +
                "AND (:userId != '0' OR :onlyActiveStatus = 0 OR sf.status IN (org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus.OPEN, org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus.CLOSED))")
        Page<SportsField> searchSportsFields(@Param("userId") String userId,
                                             @Param("text") String text,
                                             @Param("categoryId") Integer categoryId,
                                             @Param("maxPrice") Double maxPrice,
                                             @Param("minPrice") Double minPrice,
                                             @Param("onlyActiveStatus") Integer onlyActiveStatus,
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