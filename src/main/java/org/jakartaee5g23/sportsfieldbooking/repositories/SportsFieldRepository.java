package org.jakartaee5g23.sportsfieldbooking.repositories;

import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsFieldRepository extends JpaRepository<SportsField, String> {

    @Query("SELECT sf FROM SportsField sf WHERE sf.name LIKE %:text% " +
       "OR sf.location LIKE %:text% " +
       "OR sf.opacity = CAST(:text AS integer) ")
    Page<SportsField> searchByText(@Param("text") String text, Pageable pageable);

}