package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.springframework.stereotype.Service;

@Service
public interface FieldAvailabilityService {

    FieldAvailability findById(String id);

    FieldAvailability create(FieldAvailability request, boolean isConfirmed);

    void update(FieldAvailability fieldAvailability);
}
