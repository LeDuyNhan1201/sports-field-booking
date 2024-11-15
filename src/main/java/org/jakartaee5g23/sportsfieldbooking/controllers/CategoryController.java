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

    CategoryService categoryService;
    CategoryMapper categoryMapper;

    @Operation(summary = "Get all categories", description = "Get all categories when user want to see all")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories.stream()
            .map(categoryMapper::toCategoryResponse)
            .collect(Collectors.toList()));
    }

}
