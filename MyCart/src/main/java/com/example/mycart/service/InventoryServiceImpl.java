package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.InventoryDTO;
import com.example.mycart.repository.InventoryRepository;
import com.example.mycart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
//@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public class InventoryServiceImpl extends AbstractGenericService<Inventory, InventoryDTO, Long> implements InventoryService
{
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Inventory> findLowStockInventories(int threshold, Long vendorId)
    {
        return inventoryRepository.findLowStockInventories(threshold, vendorId);

    }

    @Override
    @Transactional
    public Inventory createInventory(Inventory inventory, Long productId)
    {
        var product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));

        return inventoryRepository.findByProductId(product.getId())
                .orElseGet(()->{

                    inventory.setProductId(product.getId());

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
        var product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));

        return inventoryRepository.findByProductId(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory for product " , "id",productId));

    }

    @Override
    protected JpaRepository<Inventory, Long> getRepository() {
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
