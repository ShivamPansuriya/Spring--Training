package com.example.mycart.controller;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Inventory;
import com.example.mycart.modelmapper.EntityMapper;
import com.example.mycart.modelmapper.InventoryMapper;
import com.example.mycart.payloads.InventoryDTO;
import com.example.mycart.service.InventoryService;
import com.example.mycart.service.GenericService;
import com.example.mycart.utils.Validator;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController extends AbstractGenericController<Inventory,InventoryDTO,Long>
{

    @Autowired
    private InventoryService service;

    @Autowired
    private InventoryMapper<Inventory,InventoryDTO> mapper;

    @Autowired
    private Validator validator;

    @GetMapping("/products/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProduct(@PathVariable Long productId)
    {
        if(validator.validateProduct(productId))
        {
            throw new ResourceNotFoundException("Product","id",productId);
        }
        var inventory = service.getInventoryByProduct(productId);
        return new ResponseEntity<>(mapper.toDTO(inventory,0), HttpStatus.OK);
    }

    @GetMapping("/vendor/{vendorId}/")
    public ResponseEntity<List<InventoryDTO>> getLowStockInventories(@PathVariable Long vendorId, @PathParam("threshold") int threshold)
    {
        if(validator.validateVendor(vendorId))
        {
            throw new ResourceNotFoundException("Vendor","id",vendorId);
        }
        var inventories = service.findLowStockInventories(threshold, vendorId);

        return new ResponseEntity<>(inventories.stream().map(inventory->mapper.toDTO(inventory,0)).toList(), HttpStatus.OK);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<InventoryDTO> createInventory(@Valid @RequestBody InventoryDTO inventory, @PathVariable Long productId)
    {
        if(validator.validateProduct(productId))
        {
            throw new ResourceNotFoundException("Product","id",productId);
        }
        var createdInventory = service.createInventory(mapper.toEntity(inventory), productId);

        return new ResponseEntity<>(mapper.toDTO(createdInventory,0), HttpStatus.CREATED);
    }

    @Override
    protected EntityMapper<Inventory, InventoryDTO> getMapper() {
        return mapper;
    }

    @Override
    protected GenericService<Inventory,InventoryDTO, Long> getService() {
        return service;
    }
}