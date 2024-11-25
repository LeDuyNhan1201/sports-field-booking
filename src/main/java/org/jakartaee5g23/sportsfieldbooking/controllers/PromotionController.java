package org.jakartaee5g23.sportsfieldbooking.controllers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.promotion.NewPromotionRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.promotion.UpdatePromotionRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.promotion.PromotionResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Promotion;
import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;
import org.jakartaee5g23.sportsfieldbooking.mappers.PromotionMapper;
import org.jakartaee5g23.sportsfieldbooking.services.PromotionService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.OK;

import java.util.Date;

@RestController
@RequestMapping("${api.prefix}/promotions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Promotion APIs")
public class PromotionController {

        PromotionMapper promotionMapper = PromotionMapper.INSTANCE;

        PromotionService promotionService;

        @Operation(summary = "Get promotion by id", description = "Get promotion by id", security = @SecurityRequirement(name = "bearerAuth"))
        @GetMapping("/{id}")
        public ResponseEntity<PromotionResponse> findById(@PathVariable String id) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(promotionMapper.toPromotionResponse(promotionService.findById(id)));
        }

        @Operation(summary = "Create new promotion", description = "Create a new promotion when the admin wants to use the system", security = @SecurityRequirement(name = "bearerAuth"))
        @PostMapping
        @PostAuthorize("(returnObject.body.createdBy == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
        public ResponseEntity<PromotionResponse> create(@RequestBody @Valid NewPromotionRequest request) {
                Promotion promotion = promotionMapper.toPromotion(request);
                return ResponseEntity.status(OK)
                                .body(promotionMapper.toPromotionResponse(
                                                promotionService.create(promotion, request.isConfirmed())));

        }

        @Operation(summary = "Update promotions", description = "Update promotion when admin edit promotion information", security = @SecurityRequirement(name = "bearerAuth"))
        @PutMapping("/{id}")
        @PostAuthorize("(returnObject.body.createdBy == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
        public ResponseEntity<PromotionResponse> update(@PathVariable String id,
                        @RequestBody @Valid UpdatePromotionRequest request) {
                Promotion promotion = promotionMapper.toPromotion(request);
                return ResponseEntity.status(OK)
                                .body(promotionMapper.toPromotionResponse(
                                                promotionService.update(id, promotion, request.isConfirmed())));
        }

        @Operation(summary = "Get all promotions", description = "Get all promotions when user want to see all it")
        @GetMapping
        public ResponseEntity<PaginateResponse<PromotionResponse>> findAll(
                        @RequestParam(defaultValue = "0") String offset,
                        @RequestParam(defaultValue = "100") String limit) {
                Page<Promotion> promotions = promotionService.findAll(Integer.parseInt(offset),
                                Integer.parseInt(limit));
                return ResponseEntity.status(HttpStatus.OK)
                                .body(PaginateResponse.<PromotionResponse>builder()
                                                .items(promotions.stream().map(promotionMapper::toPromotionResponse)
                                                                .toList())
                                                .pagination(new Pagination(Integer.parseInt(offset),
                                                                Integer.parseInt(limit),
                                                                promotions.getTotalElements()))
                                                .build());
        }

        @Operation(summary = "Update status promotion", description = "Update status promotion when user want change it", security = @SecurityRequirement(name = "bearerAuth"))
        @PutMapping("/status/{id}")
        @PostAuthorize("(returnObject.body.createdBy == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
        public ResponseEntity<PromotionResponse> updateStatus(@PathVariable String id,
                        @RequestParam PromotionStatus status) {
                return ResponseEntity.status(OK)
                                .body(promotionMapper.toPromotionResponse(promotionService.updateStatus(id, status)));
        }

        @Operation(summary = "Delete promotion", description = "Delete promotion when user want to delete it", security = @SecurityRequirement(name = "bearerAuth"))
        @DeleteMapping("/{id}")
        @PostAuthorize("(returnObject.body.createdBy == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
        public ResponseEntity<PromotionResponse> delete(@PathVariable String id) {
                promotionService.delete(id);
                return ResponseEntity.status(OK).build();
        }

        @Operation(summary = "Search promotion", description = "Search promotion when user want to search it")
        @GetMapping("/search")
        public ResponseEntity<PaginateResponse<PromotionResponse>> search(
                        @RequestParam(defaultValue = "") String keyword,
                        @RequestParam(required = false) PromotionStatus status,
                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                        @RequestParam(defaultValue = "0") String offset,
                        @RequestParam(defaultValue = "100") String limit) {
                Page<Promotion> promotions = promotionService.searchPromotions(keyword, status, startDate, endDate,
                                Integer.parseInt(offset),
                                Integer.parseInt(limit));
                return ResponseEntity.status(HttpStatus.OK)
                                .body(PaginateResponse.<PromotionResponse>builder()
                                                .items(promotions.stream().map(promotionMapper::toPromotionResponse)
                                                                .toList())
                                                .pagination(new Pagination(Integer.parseInt(offset),
                                                                Integer.parseInt(limit),
                                                                promotions.getTotalElements()))
                                                .build());
        }
}