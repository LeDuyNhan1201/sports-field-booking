package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailabilityAccess;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldAvailabilityAccessService {

    FieldAvailabilityAccess findById(String id);

    FieldAvailabilityAccess create(FieldAvailabilityAccess request);

    List<FieldAvailabilityAccess> findBySportsFieldId(String sportsFieldId);
}