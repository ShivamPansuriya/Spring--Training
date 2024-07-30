package com.example.mycart.service;

import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.InventoryDTO;
import com.example.mycart.repository.InventoryRepository;
import com.example.mycart.repository.SoftDeletesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl extends AbstractGenericService<Inventory, InventoryDTO, Long> implements InventoryService
{
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Inventory> findLowStockInventories(int threshold, Long vendorId)
    {
        return inventoryRepository.findLowStockInventories(threshold, vendorId);

    }

    @Override
    @Transactional
    public Inventory createInventory(Inventory inventory, Long productId)
    {
        return inventoryRepository.findByProductIdAndDeleted(productId,false)
                .orElseGet(()->{

                    inventory.setProductId(productId);

                    inventory.setUpdatedTime(LocalDateTime.now());

                    return inventoryRepository.save(inventory);
                });

    }

    @Override
    @Transactional
    public Inventory update(Long id, InventoryDTO inventoryDetails)
    {
        var inventory = findById(id);

        inventory.setQuantity(inventoryDetails.getQuantity());

        inventory.setUpdatedTime(LocalDateTime.now());

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory getInventoryByProduct(Long productId)
    {
        return inventoryRepository.findByProductIdAndDeleted(productId,false).orElse(null);
    }

    @Override
    protected SoftDeletesRepository<Inventory, Long> getRepository() {
        return inventoryRepository;
    }

    @Override
    protected Class<Inventory> getEntityClass() {
        return Inventory.class;
    }

    @Override
    protected Class<InventoryDTO> getDtoClass() {
        return InventoryDTO.class;
    }
}
