package org.jakartaee5g23.sportsfieldbooking.repositories;

import org.jakartaee5g23.sportsfieldbooking.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
    boolean existsByBookingItemId(String bookingItemId);

    Optional<Rating> findByBookingItemId(String bookingItemId);

    List<Rating> findBySportsFieldId(String sportsFieldId);
}
