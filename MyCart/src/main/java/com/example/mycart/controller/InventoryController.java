package com.example.mycart.controller;

import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.inheritDTO.InventoryDTO;
import com.example.mycart.service.InventoryService;
import com.example.mycart.service.GenericService;
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


//    @GetMapping("/")
//    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
//        var inventories = service.findAll();
//        return new ResponseEntity<>(inventories, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
//        var inventory = service.findById(id);
//        return new ResponseEntity<>(inventory, HttpStatus.OK);
//    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProduct(@PathVariable Long productId) {
        var inventory = service.getInventoryByProduct(productId);
        return new ResponseEntity<>(mapper.map(inventory,0), HttpStatus.OK);
    }

    @GetMapping("/vendor/{vendorId}/")
    public ResponseEntity<List<InventoryDTO>> getLowStockInventories(@PathVariable Long vendorId, @PathParam("threshold") int threshold)
    {
        var inventories = service.findLowStockInventories(threshold, vendorId);

        return new ResponseEntity<>(inventories.stream().map(inventory->mapper.map(inventory,0)).toList(), HttpStatus.OK);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventory, @PathVariable Long productId)
    {
        var createdInventory = service.createInventory(inventory, productId);

        return new ResponseEntity<>(mapper.map(createdInventory,0), HttpStatus.CREATED);
    }

    @Override
    protected GenericService<Inventory,InventoryDTO, Long> getService() {
        return service;
    }
}