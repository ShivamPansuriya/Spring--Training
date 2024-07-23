package com.example.mycart.controller;

import com.example.mycart.payloads.CategoryDTO;
import com.example.mycart.service.CategoryService;
import com.example.mycart.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController extends AbstractGenericController<CategoryDTO,Long> {

    @Autowired
    private CategoryService service;

//    @PostMapping
//    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
//        var newCategory = service.create(categoryDTO);
//        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
//        var category = service.findById(id);
//        return ResponseEntity.ok(category);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
//        var categories = service.findAll();
//        return ResponseEntity.ok(categories);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO)
//    {
//        categoryDTO.setId(id);
//        var updatedCategory = service.update(id, categoryDTO);
//        return ResponseEntity.ok(updatedCategory);
//    }

    @PostMapping("/{parentId}/{subId}")
    public ResponseEntity<CategoryDTO> addSubCategory(@PathVariable Long parentId, @PathVariable Long subId) {
        var updatedCategory = service.addSubCategory(subId,parentId);
        return ResponseEntity.ok(updatedCategory);
    }

    @PutMapping("/subcategories/{subId}")
    public ResponseEntity<CategoryDTO> addSubCategory(@PathVariable Long subId) {
        var updatedCategory = service.removeSubCategory(subId);
        return ResponseEntity.ok(updatedCategory);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
//        var deletedCategory = service.delete(id);
//        return new ResponseEntity<>(deletedCategory,HttpStatus.OK);
//    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<List<CategoryDTO>> getSubcategories(@PathVariable Long id) {
        var subcategories = service.getSubcategories(id);

        return ResponseEntity.ok(subcategories);
    }

    @Override
    protected GenericService<CategoryDTO, Long> getService() {
        return service;
    }
}
