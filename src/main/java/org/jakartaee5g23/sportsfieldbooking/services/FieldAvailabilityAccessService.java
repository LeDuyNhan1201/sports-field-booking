package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailabilityAccess;
import org.springframework.stereotype.Service;

@Service
public interface FieldAvailabilityAccessService {

    FieldAvailabilityAccess findById(String id);

    FieldAvailabilityAccess create(FieldAvailabilityAccess request);
}
