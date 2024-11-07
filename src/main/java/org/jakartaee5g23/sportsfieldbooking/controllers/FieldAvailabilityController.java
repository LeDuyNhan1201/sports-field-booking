package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.mappers.FieldAvailabilityMapper;
import org.jakartaee5g23.sportsfieldbooking.services.FieldAvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(summary = "Get field availability by id", description = "Get field availability detail when user have id ", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityResponse> findById(@PathVariable String id) {
        return ResponseEntity.status(OK).body(fieldAvailabilityMapper.toFieldAvailabilityResponse(fieldAvailabilityService.findById(id)));
    }
}
