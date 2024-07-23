package com.example.mycart.service;

import com.example.mycart.payloads.InventoryDTO;

import java.util.List;

public interface InventoryService extends GenericService<InventoryDTO,Long>
{
//    List<InventoryDTO> inventories();

    List<InventoryDTO> findLowStockInventories(int threshold, Long vendorId);

//    InventoryDTO getInventoryById(Long id);

    InventoryDTO createInventory(InventoryDTO inventory, Long productId);

    InventoryDTO delete(Long id);

//    InventoryDTO update(Long id, InventoryDTO inventoryDetails);

    InventoryDTO getInventoryByProduct(Long productId);
}
