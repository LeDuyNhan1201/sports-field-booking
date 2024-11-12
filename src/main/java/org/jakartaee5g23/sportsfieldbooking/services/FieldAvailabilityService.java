package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.springframework.stereotype.Service;

@Service
public interface FieldAvailabilityService {

    FieldAvailability findById(String id);

//    boolean isAlreadyOrdered(Booking booking);

    FieldAvailability create(FieldAvailability request);

    void updateFieldAvailabilityStatus();

    void update(FieldAvailability fieldAvailability);
}
