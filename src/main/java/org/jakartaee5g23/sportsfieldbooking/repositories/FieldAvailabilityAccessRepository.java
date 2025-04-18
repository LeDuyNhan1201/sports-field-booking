package org.jakartaee5g23.sportsfieldbooking.repositories;

import io.micrometer.observation.annotation.Observed;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailabilityAccess;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Observed
public interface FieldAvailabilityAccessRepository extends JpaRepository<FieldAvailabilityAccess, String> {
    List<FieldAvailabilityAccess> findBySportsField(SportsField sportsField);
}
