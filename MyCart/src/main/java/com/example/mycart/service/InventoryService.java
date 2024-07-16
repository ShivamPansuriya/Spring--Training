package com.example.mycart.service;

import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.InventoryDTO;

import java.util.List;

public interface InventoryService
{
    List<InventoryDTO> inventories();
    InventoryDTO getInventoryById(Long id);
    InventoryDTO createInventory(InventoryDTO inventory, Long productId);
    InventoryDTO deleteInventory(Long id);
    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDetails);
    InventoryDTO getInventoryByProduct(Long productId);
}
