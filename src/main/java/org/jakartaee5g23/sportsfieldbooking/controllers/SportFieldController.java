package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.SportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.services.SportFieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("${api.prefix}/sport-field")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Sport Field APIs")
public class SportFieldController {
    SportFieldService sportFieldService;

    @Operation(summary = "Edit field details", description = "Update field details when user edit sport field information", security = @SecurityRequirement(name = "bearerAuth"))

    @PutMapping("/edit-sport-field")
    public ResponseEntity<SportFieldResponse> editFieldDetails (@RequestBody @Valid SportFieldRequest request) {
        SportFieldResponse response = sportFieldService.updateField(request);
        return ResponseEntity.status(OK).body(response);
    }

    @PutMapping("/delete-sport-field")
    public ResponseEntity<SportFieldResponse> deleteSportField (@RequestBody @Valid String id) {
        SportFieldResponse res = sportFieldService.deleteField(id);
        return ResponseEntity.status(OK).body(res);
    }

}
