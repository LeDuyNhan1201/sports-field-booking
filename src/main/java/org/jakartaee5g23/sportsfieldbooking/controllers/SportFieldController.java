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
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.SportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.ListSportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.RevenueReportResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.services.SportFieldService;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Operation(summary = "Add sport field", description = "Add a field when the field manager wants to use the system", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/add-sport-field")
    public ResponseEntity<SportFieldResponse> addSportField (@RequestBody @Valid NewSportFieldRequest request) {
        SportFieldResponse response = sportFieldService.addSportField(request);
        return ResponseEntity.status(OK).body(response);
    }


    @Operation(summary = "Edit field details", description = "Update field details when user edit sport field information", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/edit-sport-field")
    public ResponseEntity<SportFieldResponse> editFieldDetails (@RequestBody @Valid SportFieldRequest request) {
        SportFieldResponse response = sportFieldService.updateField(request);
        return ResponseEntity.status(OK).body(response);
    }


    @Operation(summary = "Update status sport field", description = "Update status sport field when user want change it", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/update-status-sport-field")
    public ResponseEntity<SportFieldResponse> updateFieldStatus (@RequestBody @Valid String id, SportFieldStatus status) {
        SportFieldResponse response = sportFieldService.updateStatusField(id, status);
        return ResponseEntity.status(OK).body(response);
    }


    @Operation(summary = "Get order by status", description = "Find order by status when user want filter",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/get-order-by-status")
    public ResponseEntity<ListSportFieldResponse> getOrderByStatus( @RequestParam OrderStatus status) {
        ListSportFieldResponse listSportFieldResponse = sportFieldService.findOrderByStatus(status);
        return ResponseEntity.status(OK).body(listSportFieldResponse);
    }


    @Operation(summary = "Approve bookings", description = "User confirm or reject booking by end user", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/approve-booking")
    public ResponseEntity<SportFieldResponse> approveBooking(@RequestParam @Valid String id, OrderStatus status ){
        SportFieldResponse response = sportFieldService.updateStatusOrder(id, status);
        return ResponseEntity.status(OK).body(response);
    }


    @Operation(summary = "Revenue report", description = "Revenue report of field sport", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/revenue-report")
    public ResponseEntity<RevenueReportResponse> revenueReport(@RequestParam String id, @RequestParam Date beginDate, @RequestParam Date endDate){
        RevenueReportResponse revenueReport = sportFieldService.revenueReport(id, beginDate, endDate);
        return ResponseEntity.status(OK).body(revenueReport);
    }
    
}
