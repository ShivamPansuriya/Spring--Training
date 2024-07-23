package com.example.mycart.controller;

import com.example.mycart.service.GenericService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractGenericController<T,ID>
{

    protected abstract GenericService<T,ID> getService();

    @PostMapping
    public ResponseEntity<T> create(@Valid @RequestBody T dto)
    {
        var result = getService().create(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll()
    {
        var result = getService().findAll();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id)
    {
        var result = getService().findById(id);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable ID id)
    {
        var result = getService().delete(id);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id,@Valid @RequestBody T dto)
    {
        var result = getService().update(id,dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
