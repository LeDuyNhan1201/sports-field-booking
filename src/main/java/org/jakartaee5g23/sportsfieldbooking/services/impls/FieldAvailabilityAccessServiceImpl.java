package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailabilityAccess;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.repositories.FieldAvailabilityAccessRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportsFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.services.FieldAvailabilityAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FieldAvailabilityAccessServiceImpl implements FieldAvailabilityAccessService {
    FieldAvailabilityAccessRepository fieldAvailabilityAccessRepository;
    SportsFieldRepository sportsFieldRepository;
    @Override
    public FieldAvailabilityAccess findById(String id) {
        return fieldAvailabilityAccessRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field Availability Access"));
    }

    @Override
    public FieldAvailabilityAccess create(FieldAvailabilityAccess request) {
        SportsField sportsField = sportsFieldRepository.findById(request.getSportsField().getId())
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Sports field"));

        FieldAvailabilityAccess fieldAvailabilityAccess = FieldAvailabilityAccess.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .sportsField(sportsField)
                .build();

        return fieldAvailabilityAccessRepository.save(fieldAvailabilityAccess);
    }
}
