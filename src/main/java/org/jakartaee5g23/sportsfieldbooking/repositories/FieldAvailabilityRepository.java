package org.jakartaee5g23.sportsfieldbooking.repositories;

import io.micrometer.observation.annotation.Observed;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface FieldAvailabilityRepository extends JpaRepository<FieldAvailability, String> {

//    Optional<FieldAvailability> findByBooking(Booking booking);

}
