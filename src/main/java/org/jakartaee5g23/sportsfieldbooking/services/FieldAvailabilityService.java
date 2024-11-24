package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.enums.FieldAvailabilityStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldAvailabilityService {

    FieldAvailability findById(String id);

    FieldAvailability create(FieldAvailability request, boolean isConfirmed);

    void update(FieldAvailability fieldAvailability);

    FieldAvailability updateStatus(String id, FieldAvailabilityStatus status);

}
