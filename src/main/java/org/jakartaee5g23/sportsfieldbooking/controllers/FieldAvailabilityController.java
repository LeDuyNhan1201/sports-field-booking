package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.fieldAvailability.NewFieldAvailabilityRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.NewSportsFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.fieldAvailability.FieldAvailabilityResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.mappers.FieldAvailabilityMapper;
import org.jakartaee5g23.sportsfieldbooking.repositories.FieldAvailabilityRepository;
import org.jakartaee5g23.sportsfieldbooking.services.FieldAvailabilityService;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("${api.prefix}/field-availability")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Field Availability APIs")
public class FieldAvailabilityController {
    FieldAvailabilityMapper fieldAvailabilityMapper;

    FieldAvailabilityService fieldAvailabilityService;

    SportsFieldService sportsFieldService;

    @Operation(summary = "Get field availability by id", description = "Get field availability detail when user have id ", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityResponse> findById(@PathVariable String id) {
        return ResponseEntity.status(OK).body(fieldAvailabilityMapper.toFieldAvailabilityResponse(fieldAvailabilityService.findById(id)));
    }

    @Operation(summary = "Create new field availability", description = "Create a new field when the field manager wants to use the system", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    // @PostAuthorize("(returnObject.body.owner.id == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
    public ResponseEntity<FieldAvailabilityResponse> create(@RequestBody @Valid NewFieldAvailabilityRequest request) {
            SportsField sportsField = sportsFieldService.findById(request.sportsFieldId());
            FieldAvailability fieldAvailability = fieldAvailabilityMapper.toFieldAvailability(request);
            fieldAvailability.setSportsField(sportsField);
            return ResponseEntity.status(OK).body(
                            fieldAvailabilityMapper.toFieldAvailabilityRes(
                                            fieldAvailabilityService.create(fieldAvailability, request.isConfirmed())));
    }
}
