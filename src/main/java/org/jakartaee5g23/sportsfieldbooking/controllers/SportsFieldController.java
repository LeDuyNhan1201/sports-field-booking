package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.NewSportsFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.UpdateSportsFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.mappers.SportsFieldMapper;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.getUserIdFromContext;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("${api.prefix}/sports-field")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Sports Field APIs")
public class SportsFieldController {

    SportsFieldService sportsFieldService;

    UserService userService;

    SportsFieldMapper sportsFieldMapper = SportsFieldMapper.INSTANCE;

    @Operation(summary = "Create new sport field", description = "Create a new field when the field manager wants to use the system", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    @PostAuthorize("(returnObject.body.owner.id == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
    public ResponseEntity<SportsFieldResponse> create (@RequestBody @Valid NewSportsFieldRequest request) {
        User current = userService.findById(getUserIdFromContext());
        SportsField sportsField = sportsFieldMapper.toSportsField(request);
        sportsField.setUser(current);
        sportsField.setCategory(Category.builder().id(request.categoryId()).build());
        return ResponseEntity.status(OK).body(sportsFieldMapper.toSportsFieldResponse(sportsFieldService.create(sportsField, request.isConfirmed())));
    }


    @Operation(summary = "Update field details", description = "Update field details when user edit sport field information", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    @PostAuthorize("(returnObject.body.owner.id == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
    public ResponseEntity<SportsFieldResponse> update(@RequestBody @Valid UpdateSportsFieldRequest request) {
        SportsField sportsField = sportsFieldMapper.toSportsField(request);
        return ResponseEntity.status(OK).body(sportsFieldMapper.toSportsFieldResponse(sportsFieldService.update(sportsField, request.isConfirmed())));
    }

    @Operation(summary = "Update status sport field", description = "Update status sport field when user want change it", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/status/{id}")
    // @PostAuthorize("(returnObject.body.owner.id == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
    public ResponseEntity<SportsFieldResponse> updateStatus (@PathVariable String id, @RequestParam SportsFieldStatus status) {
        return ResponseEntity.status(OK).body(sportsFieldMapper.toSportsFieldResponse(sportsFieldService.updateStatus(id, status)));
    }

    @Operation(summary = "Get all sport fields", description = "Get all sport fields when user want to see all fields")
    @GetMapping
    public ResponseEntity<PaginateResponse<SportsFieldResponse>> findAll(@RequestParam String colSort,
                                                                         @RequestParam Integer sortDirection,
                                                                         @RequestParam(defaultValue = "0") String offset,
                                                                         @RequestParam(defaultValue = "100") String limit) {
        Page<SportsField> sportFields = sportsFieldService.findAll(Integer.parseInt(offset), Integer.parseInt(limit), colSort, sortDirection);
        return ResponseEntity.status(HttpStatus.OK)
                .body(PaginateResponse.<SportsFieldResponse>builder()
                        .items(sportFields.stream().map(sportsFieldMapper::toSportsFieldResponse).toList())
                        .pagination(new Pagination(Integer.parseInt(offset), Integer.parseInt(limit), sportFields.getTotalElements()))
                        .build());
    }
    
    @Operation(summary = "Get sport field by id", description = "Get sport field detail when user have id ", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    // @PostAuthorize("(returnObject.body.owner.id == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
    public ResponseEntity<SportsFieldResponse> findById (@PathVariable String id) {
        return ResponseEntity.status(OK).body(sportsFieldMapper.toSportsFieldResponse(sportsFieldService.findById(id)));
    }

    @Operation(summary = "Search sport fields by text", description = "Search sport fields by name, location, or description containing the given text")

    @GetMapping("/search/{text}")
    public ResponseEntity<PaginateResponse<SportsFieldResponse>> searchByText(@PathVariable String text,
                                                                            @RequestParam String colSort,
                                                                            @RequestParam Integer sortDirection,
                                                                            @RequestParam(defaultValue = "0") String offset,
                                                                            @RequestParam(defaultValue = "100") String limit) {
        Page<SportsField> sportFields = sportsFieldService.searchByText(text, Integer.parseInt(offset), Integer.parseInt(limit), colSort, sortDirection);
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(PaginateResponse.<SportsFieldResponse>builder()
                        .items(sportFields.stream().map(sportsFieldMapper::toSportsFieldResponse).toList())
                        .pagination(new Pagination(Integer.parseInt(offset), Integer.parseInt(limit), sportFields.getTotalElements()))
                        .build());
    }



}
