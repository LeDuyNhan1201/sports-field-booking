package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.fieldAvailability.FieldAvailabilityAccessRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.fieldAvailability.NewFieldAvailabilityRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityAccessResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailabilityAccess;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.enums.FieldAvailabilityStatus;
import org.jakartaee5g23.sportsfieldbooking.mappers.FieldAvailabilityAccessMapper;
import org.jakartaee5g23.sportsfieldbooking.services.FieldAvailabilityAccessService;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("${api.prefix}/field-availability-access")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Field Availability Access APIs")
public class FieldAvailabilityAccessController {
    FieldAvailabilityAccessMapper fieldAvailabilityAccessMapper;
    FieldAvailabilityAccessService fieldAvailabilityAccessService;
    SportsFieldService sportsFieldService;

    @Operation(summary = "Get field availability access by id", description = "Get field availability access by id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<FieldAvailabilityAccessResponse> findById(@PathVariable String id) {
        return ResponseEntity.status(OK).body(fieldAvailabilityAccessMapper.toFieldAvailabilityAccessResponse(fieldAvailabilityAccessService.findById(id)));
    }

    @Operation(summary = "Get field availability access by sport field id", description = "Get field availability access by sport field", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/sports-field")
    public ResponseEntity<List<FieldAvailabilityAccessResponse>> findBySportFieldID(@RequestParam String sportsFieldID) {
        List<FieldAvailabilityAccess> fieldAvailabilityAccessList = fieldAvailabilityAccessService.findBySportsFieldId(sportsFieldID);
        List<FieldAvailabilityAccessResponse> responseList = fieldAvailabilityAccessMapper.toFieldAvailabilityAccessResponseList(fieldAvailabilityAccessList);
        return ResponseEntity.status(OK).body(responseList);
    }

    @Operation(summary = "Create a new field availability access", description = "Create a new field availability access", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/create")
    public ResponseEntity<FieldAvailabilityAccessResponse> create(@RequestBody @Valid FieldAvailabilityAccessRequest request) {
        SportsField sportsField = sportsFieldService.findById(request.sportsFieldId());
        FieldAvailabilityAccess fieldAvailabilityAccess = FieldAvailabilityAccess.builder()
                .startDate(request.startTime())
                .endDate(request.endTime())
                .sportsField(sportsField)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(fieldAvailabilityAccessMapper.toFieldAvailabilityAccessResponse(fieldAvailabilityAccessService.create(fieldAvailabilityAccess)));
    }
}
