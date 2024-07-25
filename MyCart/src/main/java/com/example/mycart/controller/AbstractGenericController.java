package com.example.mycart.controller;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.payloads.BaseDTO;
import com.example.mycart.service.GenericService;
import com.example.mycart.modelmapper.EntityMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractGenericController<T extends BaseEntity<Long>,D extends BaseDTO,ID>
{
    protected abstract EntityMapper<T,D> getMapper();

    protected abstract GenericService<T,D,ID> getService();

    @PostMapping
    public ResponseEntity<D> create(@Valid @RequestBody D dto)
    {
        var result = getService().create(getMapper().toEntity(dto));

        return new ResponseEntity<>(getMapper().toDTO(result,0), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<D>> getAll(@RequestParam(defaultValue = "0", required = false) int pageNo)
    {
        var result = getService().findAll(pageNo);
        return new ResponseEntity<>(getMapper().toDTOs(result,pageNo), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable ID id, @RequestParam(defaultValue = "0", required = false) int pageNo)
    {
        var result = getService().findById(id);

        return new ResponseEntity<>(getMapper().toDTO(result,pageNo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<D> delete(@PathVariable ID id, @RequestParam(defaultValue = "0", required = false) int pageNo)
    {
        var result = getService().delete(id);

        return new ResponseEntity<>(getMapper().toDTO(result,pageNo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable ID id,@Valid @RequestBody D dto, @RequestParam(defaultValue = "0", required = false) int pageNo)
    {
        var result = getService().update(id,dto);

        return new ResponseEntity<>(getMapper().toDTO(result,pageNo), HttpStatus.CREATED);
    }
}
