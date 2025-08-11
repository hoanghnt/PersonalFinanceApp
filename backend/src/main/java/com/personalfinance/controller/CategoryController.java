package com.personalfinance.controller;

import com.personalfinance.dto.request.CategoryRequest;
import com.personalfinance.dto.response.CategoryResponse;
import com.personalfinance.dto.response.MessageResponse;
import com.personalfinance.entity.Category;
import com.personalfinance.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category Management", description = "APIs for managing categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //Get all categories
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get all categories", description = "Retrieve all non-deleted categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    //Get categories by type
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get categories by type", description = "Retrieve categories filtered by type (INCOME or EXPENSE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<CategoryResponse>> getCategoriesByType(@PathVariable("type") Category.CategoryType type) {
        List<CategoryResponse> categories = categoryService.getCategoriesByType(type);
        return ResponseEntity.ok(categories);
    }

    //Get category by id
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get category by ID", description = "Retrieve a specific category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id).map(category -> ResponseEntity.ok(category)).orElse(ResponseEntity.notFound().build());
    }

    //Create new category
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new category", description = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or category already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest request) {
        try {
            CategoryResponse category = categoryService.createCategory(request);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    //Update category
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update category", description = "Update an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or category not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequest request) {
        try {
            CategoryResponse category = categoryService.updateCategory(id, request);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    //Delete category
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete category", description = "Soft delete a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Category not found or already deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new MessageResponse("Category deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    //Search category
    @PreAuthorize("permitAll()")
    @Operation(summary = "Search categories", description = "Search categories by name (case insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponse>> searchCategory(@RequestParam String name) {
        List<CategoryResponse> categories = categoryService.searchCategory(name);
        return ResponseEntity.ok(categories);
    }
}
