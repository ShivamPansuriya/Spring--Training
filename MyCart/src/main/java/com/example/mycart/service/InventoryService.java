package com.example.mycart.service;

import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.inheritDTO.InventoryDTO;

import java.util.List;

public interface InventoryService extends GenericService<Inventory,InventoryDTO,Long>
{
//    List<InventoryDTO> inventories();

    List<Inventory> findLowStockInventories(int threshold, Long vendorId);

//    InventoryDTO getInventoryById(Long id);

    Inventory createInventory(InventoryDTO inventory, Long productId);

//    InventoryDTO update(Long id, InventoryDTO inventoryDetails);

    Inventory getInventoryByProduct(Long productId);
}
