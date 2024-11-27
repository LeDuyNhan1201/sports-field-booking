package org.jakartaee5g23.sportsfieldbooking.repositories;

import io.micrometer.observation.annotation.Observed;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Observed
public interface BookingItemRepository extends JpaRepository<BookingItem, String> {

    List<BookingItem> findAllByBooking(Booking booking);
    List<BookingItem> findBySportsFieldId(String sportsFieldId);
    @Query("SELECT bi FROM BookingItem bi " +
            "JOIN bi.sportsField sf " +
            "WHERE sf.user.id = :fieldOwnerId")
    Page<BookingItem> findBookingItemsByFieldOwner(@Param("fieldOwnerId") String fieldOwnerId, Pageable pageable);
}
