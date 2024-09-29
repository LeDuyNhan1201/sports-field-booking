package org.jakartaee5g23.sportsfieldbooking.repositories;

import java.util.*;

import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query("SELECT b FROM Booking b WHERE b.payment != NULL AND b.sportField = :sportField AND b.startTime BETWEEN :beginDate AND :endDate")
    Page<Booking> findBySportFieldIdAndStartTimeBetween(
            SportField sportField,
            Date beginDate,
            Date endDate,
            Pageable pageable);

    @Query("SELECT b FROM Booking b " +
            //"JOIN o.fieldAvailability f " +
            "WHERE b.user.id = :userId " +
            "AND b.startTime > CURRENT_TIMESTAMP")
    Page<Booking> findUpcomingBookingsByUserId(String userId, Pageable pageable);

    Page<Booking> findAllByUser(User user, Pageable pageable);

    Page<Booking> findAllByStatus(BookingStatus status, Pageable pageable);

}