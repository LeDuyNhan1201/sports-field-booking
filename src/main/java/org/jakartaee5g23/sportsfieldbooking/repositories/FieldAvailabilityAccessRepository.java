package org.jakartaee5g23.sportsfieldbooking.repositories;

import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailabilityAccess;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldAvailabilityAccessRepository extends JpaRepository<FieldAvailabilityAccess, String> {
    List<FieldAvailabilityAccess> findBySportsField(SportsField sportsField);
}
