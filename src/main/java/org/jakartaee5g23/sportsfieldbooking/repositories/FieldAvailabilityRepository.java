package org.jakartaee5g23.sportsfieldbooking.repositories;

import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldAvailabilityRepository extends JpaRepository<FieldAvailability, String> {

    Optional<FieldAvailability> findByBooking(Booking booking);

}
