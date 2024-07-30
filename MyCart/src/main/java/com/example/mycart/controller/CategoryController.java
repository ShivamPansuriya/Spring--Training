package com.example.mycart.controller;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Category;
import com.example.mycart.modelmapper.CategoryMapper;
import com.example.mycart.modelmapper.EntityMapper;
import com.example.mycart.payloads.CategoryDTO;
import com.example.mycart.service.CategoryService;
import com.example.mycart.service.GenericService;
import com.example.mycart.utils.Validator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.mycart.constants.Constants.CATEGORY;
import static com.example.mycart.constants.Constants.ID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController extends AbstractGenericController<Category,CategoryDTO,Long> {

    private final CategoryService service;

    private final CategoryMapper<Category,CategoryDTO> mapper;

    private final Validator validator;

    public CategoryController(CategoryService service, CategoryMapper<Category, CategoryDTO> mapper, Validator validator) {
        this.service = service;
        this.mapper = mapper;
        this.validator = validator;
    }

    @PostMapping("/{parentId}/{subId}")
    public ResponseEntity<CategoryDTO> addSubCategory(@PathVariable Long parentId, @PathVariable Long subId)
    {
        if(validator.validateCategory(parentId))
        {
            throw new ResourceNotFoundException(CATEGORY,ID,parentId);
        }

        if(validator.validateCategory(subId))
        {
            throw new ResourceNotFoundException(CATEGORY,ID,subId);
        }

        var updatedCategory = service.addSubCategory(subId,parentId);

        return new ResponseEntity<>(mapper.toDTO(updatedCategory,0),HttpStatus.CREATED);
    }

    @PutMapping("/subcategories/{subId}")
    public ResponseEntity<CategoryDTO> removeSubCategory(@PathVariable Long subId) {
        if(validator.validateCategory(subId))
        {
            throw new ResourceNotFoundException(CATEGORY,ID,subId);
        }

        var updatedCategory = service.removeSubCategory(subId);

        return ResponseEntity.ok(mapper.toDTO(updatedCategory,0));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<Page<CategoryDTO>> getSubcategories(@PathVariable Long id,@RequestParam(defaultValue = "0", required = false) int pageNo) {
        var subcategories = service.getSubcategories(id,pageNo);

        return new ResponseEntity<>(subcategories.map(category ->mapper.toDTO(category,pageNo)), HttpStatus.OK);
    }

    @Override
    protected EntityMapper<Category, CategoryDTO> getMapper() {
        return mapper;
    }

    @Override
    protected GenericService<Category,CategoryDTO, Long> getService() {
        return service;
    }
}
