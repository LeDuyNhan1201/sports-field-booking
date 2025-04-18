package org.jakartaee5g23.sportsfieldbooking.repositories;

import java.util.*;

import io.micrometer.observation.annotation.Observed;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface BookingRepository extends JpaRepository<Booking, String> {

        // @Query("SELECT b FROM Booking b " +
        // "JOIN b.fieldAvailability f " +
        // "WHERE b.payment != NULL " +
        // "AND f.sportsField = :sportsField " +
        // "AND f.startTime BETWEEN :beginDate AND :endDate")
        // Page<Booking> findBySportFieldIdAndStartTimeBetween(
        // SportsField sportsField,
        // Date beginDate,
        // Date endDate,
        // Pageable pageable);

        // @Query("SELECT b FROM Booking b " +
        // "JOIN b.fieldAvailability f " +
        // "WHERE b.user.id = :userId " +
        // "AND f.startTime > CURRENT_TIMESTAMP")
        // Page<Booking> findUpcomingBookingsByUserId(String userId, Pageable pageable);

        Page<Booking> findAllByUser(User user, Pageable pageable);

        Page<Booking> findAllByStatus(BookingStatus status, Pageable pageable);

        Page<Booking> findAllByUserAndStatusIn(User user, List<BookingStatus> statuses, Pageable pageable);

        @Query("SELECT b FROM Booking b WHERE b.createdAt BETWEEN :startDate AND :endDate")
        List<Booking> findBookingsByDateRange(
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);

        @Query("SELECT DISTINCT b FROM Booking b " +
                "JOIN b.bookingItems bi " +
                "JOIN bi.sportsField sf " +
                "JOIN b.user u " +
                "WHERE sf.user.id = :fieldOwnerId " +
                "AND (:keyword IS NULL OR " +
                "       b.id LIKE CONCAT('%', :keyword, '%') OR " +
                "       u.username LIKE CONCAT('%', :keyword, '%')) " +
                "AND (:status IS NULL OR b.status = :status) " +
                "AND (:startDate IS NULL OR :endDate IS NULL OR b.createdAt BETWEEN :startDate AND :endDate)")
        Page<Booking> searchBookings(
                @Param("fieldOwnerId") String fieldOwnerId,
                @Param("keyword") String keyword,
                @Param("status") BookingStatus status,
                @Param("startDate") Date startDate,
                @Param("endDate") Date endDate,
                Pageable pageable);


        @Query("SELECT DISTINCT b FROM Booking b " +
                "JOIN b.bookingItems bi " +
                "JOIN bi.sportsField sf " +
                "WHERE sf.user.id = :fieldOwnerId")
        Page<Booking> findBookingsByFieldOwner(@Param("fieldOwnerId") String fieldOwnerId, Pageable pageable);
}