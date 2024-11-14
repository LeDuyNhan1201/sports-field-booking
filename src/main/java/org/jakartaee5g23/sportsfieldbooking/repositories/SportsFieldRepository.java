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
    @Query("SELECT sf FROM SportsField sf WHERE sf.name LIKE %:text% " +
            "OR sf.location LIKE %:text% " +
            "OR sf.category.id = CAST(:text AS integer) " +
            "OR sf.opacity = CAST(:text AS integer) ")
    Page<SportsField> findSportsFieldsByKeyword(@Param("text") String text, Pageable pageable);

    @Query("SELECT sf FROM SportsField sf WHERE (sf.name LIKE %:text% " +
            "OR sf.location LIKE %:text% " +
            "OR sf.opacity = CAST(:text AS integer) " +
            "OR sf.category.id = CAST(:text AS integer)) " +
            "AND sf.user.id LIKE %:userId%")
    Page<SportsField> findSportsFieldsByKeywordAndUserId(@Param("userId") String userId, @Param("text") String text, Pageable pageable);


}