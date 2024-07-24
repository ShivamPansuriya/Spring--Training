package com.example.mycart.controller;

import com.example.mycart.model.Category;
import com.example.mycart.payloads.inheritDTO.CategoryDTO;
import com.example.mycart.service.CategoryService;
import com.example.mycart.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController extends AbstractGenericController<Category,CategoryDTO,Long> {

    @Autowired
    private CategoryService service;

    @PostMapping("/{parentId}/{subId}")
    public ResponseEntity<CategoryDTO> addSubCategory(@PathVariable Long parentId, @PathVariable Long subId) {
        var updatedCategory = service.addSubCategory(subId,parentId);
        return new ResponseEntity<>(mapper.map(updatedCategory,0),HttpStatus.CREATED);
    }

    @PutMapping("/subcategories/{subId}")
    public ResponseEntity<CategoryDTO> addSubCategory(@PathVariable Long subId) {
        var updatedCategory = service.removeSubCategory(subId);
        return ResponseEntity.ok(mapper.map(updatedCategory,0));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<Page<CategoryDTO>> getSubcategories(@PathVariable Long id,@RequestParam(defaultValue = "0") int pageNo) {
        var subcategories = service.getSubcategories(id,pageNo);

        return new ResponseEntity<>(subcategories.map(category ->mapper.map(category,pageNo)), HttpStatus.OK);
    }

    @Override
    protected GenericService<Category,CategoryDTO, Long> getService() {
        return service;
    }
}
