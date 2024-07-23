package com.example.mycart.controller;

import com.example.mycart.payloads.InventoryDTO;
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
public class InventoryController extends AbstractGenericController<InventoryDTO,Long>
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
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("/vendor/{vendorId}/")
    public ResponseEntity<List<InventoryDTO>> getLowStockInventories(@PathVariable Long vendorId, @PathParam("threshold") int threshold)
    {
        var inventory = service.findLowStockInventories(threshold, vendorId);

        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventory, @PathVariable Long productId)
    {
        var createdInventory = service.createInventory(inventory, productId);

        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO)
//    {
//        inventoryDTO.setId(id);
//        var updatedInventory = service.update(id, inventoryDTO);
//        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<InventoryDTO> deleteInventory(@PathVariable Long id) {
//        var deletedInventory = service.delete(id);
//        return new ResponseEntity<>(deletedInventory,HttpStatus.OK);
//    }

    @Override
    protected GenericService<InventoryDTO, Long> getService() {
        return service;
    }
}