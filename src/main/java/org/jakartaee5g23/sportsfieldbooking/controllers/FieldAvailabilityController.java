package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.fieldAvailability.NewFieldAvailabilityRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.NewSportsFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.FieldAvailabilityStatus;
import org.jakartaee5g23.sportsfieldbooking.mappers.FieldAvailabilityMapper;
import org.jakartaee5g23.sportsfieldbooking.repositories.FieldAvailabilityRepository;
import org.jakartaee5g23.sportsfieldbooking.services.FieldAvailabilityService;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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

    @Operation(summary = "Update field availability status", description = "Update field availability status", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/update-status/{id}")
    public ResponseEntity<FieldAvailabilityResponse> updateStatus(@PathVariable String id, @RequestParam FieldAvailabilityStatus status) {
        System.out.println("test thu data: "+id+" "+status);
        FieldAvailability updatedFieldAvailability = fieldAvailabilityService.updateStatus(id, status);
        return ResponseEntity.ok(fieldAvailabilityMapper.toFieldAvailabilityResponse(updatedFieldAvailability));
    }


    @Operation(summary = "Create new field availability", description = "Create a new field when the field manager wants to use the system", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/create")
//    @PostAuthorize("returnObject.body.MUser.id == authentication.name")
    public ResponseEntity<Void> create(@RequestBody @Valid NewFieldAvailabilityRequest request) {
        SportsField sportsField = sportsFieldService.findById(request.sportsFieldId());
        FieldAvailability fieldAvailability = FieldAvailability.builder()
                .startTime(request.startTime())
                .endTime(request.endTime())
                .price(request.price())
                .sportsField(sportsField)
                .status(FieldAvailabilityStatus.AVAILABLE)
                .build();
        fieldAvailability.setSportsField(sportsField);
        fieldAvailabilityService.create(fieldAvailability, request.isConfirmed());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}