package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.mappers.CategoryMapper;
import org.jakartaee5g23.sportsfieldbooking.services.CategoryService;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.category.CategoryResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Category APIs")
public class CategoryController {
    final CategoryService categoryService;
    final CategoryMapper categoryMapper;

    @Operation(summary = "Get all categories", description = "Get all categories when user want to see all")
    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> findAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Category> categories = categoryService.findAll(pageable);
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponses);
    }
}