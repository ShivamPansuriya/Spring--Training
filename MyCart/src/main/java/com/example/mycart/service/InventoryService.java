package com.example.mycart.service;

import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.InventoryDTO;

import java.util.List;

public interface InventoryService extends GenericService<Inventory,InventoryDTO,Long>
{
    List<Inventory> findLowStockInventories(int threshold, Long vendorId);

    Inventory createInventory(Inventory inventory, Long productId);

    Inventory getInventoryByProduct(Long productId);
}
