package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.repositories.FieldAvailabilityRepository;
import org.jakartaee5g23.sportsfieldbooking.services.FieldAvaibilityService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FieldAvaibilityServiceImpl implements FieldAvaibilityService {
    FieldAvailabilityRepository fieldAvailabilityRepository;
    @Override
    @Scheduled(fixedRate = 30000) // 30s
    public void updateFieldAvailabilityStatus() {
        Date currentTime = new Date();

        List<FieldAvailability> fieldAvailabilityList = fieldAvailabilityRepository.findAll();

        for(FieldAvailability fieldAvailability : fieldAvailabilityList) {
            if(fieldAvailability.getEndTime().before(currentTime)) {
                fieldAvailability.setIsAvailable(false);
                fieldAvailabilityRepository.save(fieldAvailability);
            }
        }
    }
}
