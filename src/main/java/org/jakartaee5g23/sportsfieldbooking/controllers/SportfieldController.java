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
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.mappers.SportFieldMapper;
import org.jakartaee5g23.sportsfieldbooking.services.SportFieldService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.getUserIdFromContext;
import static org.springframework.http.HttpStatus.OK;


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

    @Operation(summary = "Create new sport field", description = "Create a new field when the field manager wants to use the system", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    @PostAuthorize("(returnObject.body.owner.id == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
    public ResponseEntity<SportFieldResponse> create (@RequestBody @Valid NewSportFieldRequest request) {
        User current = userService.findById(getUserIdFromContext());
        SportField sportField = sportFieldMapper.toSportField(request);
        sportField.setUser(current);
        sportField.setCategory(Category.builder().id(request.categoryId()).build());
        return ResponseEntity.status(OK).body(sportFieldMapper.toSportFieldResponse(sportFieldService.create(sportField, request.isConfirmed())));
    }


    @Operation(summary = "Update field details", description = "Update field details when user edit sport field information", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    @PostAuthorize("(returnObject.body.owner.id == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
    public ResponseEntity<SportFieldResponse> update(@RequestBody @Valid UpdateSportFieldRequest request) {
        SportField sportField = sportFieldMapper.toSportField(request);
        return ResponseEntity.status(OK).body(sportFieldMapper.toSportFieldResponse(sportFieldService.update(sportField, request.isConfirmed())));
    }

    @Operation(summary = "Update status sport field", description = "Update status sport field when user want change it", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/status/{id}")
    @PostAuthorize("(returnObject.body.owner.id == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
    public ResponseEntity<SportFieldResponse> updateStatus (@PathVariable String id, @RequestParam SportFieldStatus status) {
        return ResponseEntity.status(OK).body(sportFieldMapper.toSportFieldResponse(sportFieldService.updateStatus(id, status)));
    }

    @Operation(summary = "Get all sport fields", description = "Get all sport fields when user want to see all fields", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<PaginateResponse<SportFieldResponse>> findAll(@RequestParam(defaultValue = "0") String offset,
                                                                     @RequestParam(defaultValue = "100") String limit) {
        Page<SportField> sportFields = sportFieldService.findAll(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseEntity.status(HttpStatus.OK)
                .body(PaginateResponse.<SportFieldResponse>builder()
                        .items(sportFields.stream().map(sportFieldMapper::toSportFieldResponse).toList())
                        .pagination(new Pagination(Integer.parseInt(offset), Integer.parseInt(limit), sportFields.getTotalElements()))
                        .build());
    }
    
}
