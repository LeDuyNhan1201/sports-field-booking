package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.mappers.CategoryMapper;
import org.jakartaee5g23.sportsfieldbooking.services.CategoryService;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.categories.CategoryRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.category.CategoryResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Category APIs")
public class CategoryController {
        final CategoryService categoryService;
        final CategoryMapper categoryMapper;

        @Operation(summary = "Get all categories", description = "Get all categories when user wants to see all")
        @GetMapping
        public ResponseEntity<PaginateResponse<CategoryResponse>> findAll(
                        @RequestParam(defaultValue = "0") String offset,
                        @RequestParam(defaultValue = "10") String limit) {
                int page = Integer.parseInt(offset);
                int size = Integer.parseInt(limit);
                Pageable pageable = PageRequest.of(page, size);
                Page<Category> categories = categoryService.findAll(pageable);
                List<CategoryResponse> categoryResponses = categories.stream()
                                .map(categoryMapper::toCategoryResponse)
                                .toList();

                PaginateResponse<CategoryResponse> response = PaginateResponse.<CategoryResponse>builder()
                                .items(categoryResponses)
                                .pagination(new Pagination(page, size, categories.getTotalElements()))
                                .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Add new category", description = "Add new category when admin wants to add new category")
        @PostMapping
        // @PostAuthorize("(returnObject.body.createdBy == authentication.name and
        // hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
        public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest category) {
                Category newCategory = categoryMapper.toCategory(category);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(categoryMapper.toCategoryResponse(categoryService.create(newCategory)));
        }

        @Operation(summary = "Update category", description = "Update category when admin wants to update category")
        @PutMapping("/{id}")
        // @PostAuthorize("(returnObject.body.createdBy == authentication.name and
        // hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
        public ResponseEntity<CategoryResponse> update(@PathVariable int id,
                        @RequestBody @Valid CategoryRequest category) {
                Category updateCategory = categoryMapper.toCategory(category);
                return ResponseEntity.status(HttpStatus.OK)
                                .body(categoryMapper.toCategoryResponse(categoryService.update(id, updateCategory)));
        }

        @Operation(summary = "Delete category", description = "Delete category when admin wants to delete category")
        @DeleteMapping("/{id}")
        @PostAuthorize("(returnObject.body.createdBy == authentication.name and hasRole('FIELD_OWNER')) or hasRole('ADMIN')")
        public ResponseEntity<Void> delete(@PathVariable int id) {
                categoryService.delete(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @Operation(summary = "Search category", description = "Search category by id or name")
        @GetMapping("/search")
        public ResponseEntity<PaginateResponse<CategoryResponse>> search(
                        @RequestParam(defaultValue = "") String keyword,
                        @RequestParam(defaultValue = "0") String offset,
                        @RequestParam(defaultValue = "100") String limit) {
                Page<Category> categories = categoryService.searchCategories(keyword, Integer.parseInt(offset),
                                Integer.parseInt(limit));
                List<CategoryResponse> categoryResponses = categories.stream()
                                .map(categoryMapper::toCategoryResponse)
                                .toList();
                PaginateResponse<CategoryResponse> response = PaginateResponse.<CategoryResponse>builder()
                                .items(categoryResponses)
                                .pagination(new Pagination(Integer.parseInt(offset),
                                                Integer.parseInt(limit), categories.getTotalElements()))
                                .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }
}