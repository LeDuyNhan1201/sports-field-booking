package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.NewSportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.UpdateSportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.RevenueReportResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.mappers.SportFieldMapper;
import org.jakartaee5g23.sportsfieldbooking.services.SportFieldService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.getUserIdFromContext;
import static org.springframework.http.HttpStatus.OK;

import java.util.*;


@RestController
@RequestMapping("${api.prefix}/sport-field")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Sport Field APIs")
public class SportFieldController {

    SportFieldService sportFieldService;

    UserService userService;

    SportFieldMapper sportFieldMapper = SportFieldMapper.INSTANCE;

    @Operation(summary = "Add sport field", description = "Add a field when the field manager wants to use the system", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/add-sport-field")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FIELD_OWNER')")
    public ResponseEntity<SportFieldResponse> addSportField (@RequestBody @Valid NewSportFieldRequest request) {
        User current = userService.findById(getUserIdFromContext());
        SportField sportField = sportFieldMapper.toSportField(request);
        sportField.setUser(current);
        sportField.setCategory(Category.builder().id(request.categoryId()).build());
        return ResponseEntity.status(OK).body(sportFieldMapper.toSportFieldResponse(sportFieldService.create(sportField, request.isConfirmed())));
    }


    @Operation(summary = "Edit field details", description = "Update field details when user edit sport field information", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    public ResponseEntity<SportFieldResponse> editFieldDetails(@RequestBody @Valid UpdateSportFieldRequest request) {
        SportField sportField = sportFieldMapper.toSportField(request);
        return ResponseEntity.status(OK).body(sportFieldMapper.toSportFieldResponse(sportFieldService.update(sportField, request.isConfirmed())));
    }

    @Operation(summary = "Update status sport field", description = "Update status sport field when user want change it", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<SportFieldResponse> updateFieldStatus (@PathVariable String id, SportFieldStatus status) {
        return ResponseEntity.status(OK).body(sportFieldMapper.toSportFieldResponse(sportFieldService.updateStatus(id, status)));
    }

    @Operation(summary = "Revenue report", description = "Revenue report of field sport", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/revenue-report/{id}")
    public ResponseEntity<RevenueReportResponse> revenueReport(@PathVariable String id,
                                                               @RequestParam Date beginDate, @RequestParam Date endDate,
                                                               @RequestParam(defaultValue = "0") String offset,
                                                               @RequestParam(defaultValue = "100") String limit) {
        SportField sportField = sportFieldService.findById(id);
        return ResponseEntity.status(OK)
                .body(sportFieldService.revenueReport(sportField, beginDate, endDate, Integer.parseInt(offset), Integer.parseInt(limit)));
    }
    
}
