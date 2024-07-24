package com.example.mycart.controller;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.payloads.inheritDTO.BaseDTO;
import com.example.mycart.service.GenericService;
import com.example.mycart.utils.EntityMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractGenericController<T extends BaseEntity<Long>,D extends BaseDTO,ID>
{
    @Autowired
    protected EntityMapper<T,D> mapper;

    protected abstract GenericService<T,D,ID> getService();

    @PostMapping
    public ResponseEntity<D> create(@Valid @RequestBody D dto)
    {
        var result = getService().create(dto);

        return new ResponseEntity<>(mapper.map(result,0), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<D>> getAll(@RequestParam(defaultValue = "0") int pageNo)
    {
        var result = getService().findAll(pageNo);
        return new ResponseEntity<>(result.map(entity->mapper.map(entity,pageNo)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable ID id, @RequestParam(defaultValue = "0") int pageNo)
    {
        var result = getService().findById(id);

        return new ResponseEntity<>(mapper.map(result,pageNo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<D> delete(@PathVariable ID id)
    {
        var result = getService().delete(id);

        return new ResponseEntity<>(mapper.map(result,0), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable ID id,@Valid @RequestBody D dto)
    {
        var result = getService().update(id,dto);

        return new ResponseEntity<>(mapper.map(result,0), HttpStatus.CREATED);
    }
}
