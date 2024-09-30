package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.dashboard.RevenueReportResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.services.DashboardService;
import org.jakartaee5g23.sportsfieldbooking.services.SportFieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("${api.prefix}/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Dashboard APIs")
public class DashboardController {

    DashboardService dashboardService;

    SportFieldService sportFieldService;

    @Operation(summary = "Revenue report", description = "Revenue report of field sport", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/revenue-report/{id}")
    @PostAuthorize("(returnObject.body.owner == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
    public ResponseEntity<RevenueReportResponse> revenueReport(@PathVariable String id,
                                                               @RequestParam Date beginDate, @RequestParam Date endDate,
                                                               @RequestParam(defaultValue = "0") String offset,
                                                               @RequestParam(defaultValue = "100") String limit) {
        SportField sportField = sportFieldService.findById(id);
        return ResponseEntity.status(OK)
                .body(dashboardService.revenueReport(sportField, beginDate, endDate, Integer.parseInt(offset), Integer.parseInt(limit)));
    }

}
