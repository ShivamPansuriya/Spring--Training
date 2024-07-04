package com.example.ecommerce.controllers;

import com.example.ecommerce.constants.Constants;
import com.example.ecommerce.paylods.CategoryDTO;
import com.example.ecommerce.paylods.CategoryResponse;
import com.example.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.SORT_BY, required = false) String  sortBy,
            @RequestParam(name = "sortOrder", defaultValue = Constants.SORT_ORDER, required = false) String sortOrder
    ) {

        var categories = categoryService.getCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDto) {
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{category}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable long category) {

        return ResponseEntity.ok(categoryService.deleteCategory(category));
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDto, @PathVariable long categoryId) {
        return ResponseEntity.ok( categoryService.updateCategory(categoryDto, categoryId));
    }
}
