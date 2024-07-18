package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.InventoryDTO;
import com.example.mycart.repository.InventoryRepository;
import com.example.mycart.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public class InventoryServiceImpl implements InventoryService
{
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<InventoryDTO> inventories()
    {
        var inventories = inventoryRepository.findAll();

        return inventories.stream().map(inventory-> mapper.map(inventory,InventoryDTO.class)).toList();
    }

    @Override
    public List<InventoryDTO> findLowStockInventories(int threshold, Long vendorId)
    {
        var inventories = inventoryRepository.findLowStockInventories(threshold, vendorId);

        return inventories.stream()
                .map(inventory -> mapper.map(inventory, InventoryDTO.class))
                .toList();
    }

    public Inventory findInventoryById(Long id)
    {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id" , id));
    }

    @Override
    @Cacheable
    public InventoryDTO getInventoryById(Long id)
    {
        var inventory = findInventoryById(id);

        return mapper.map(inventory,InventoryDTO.class);
    }

    @Override
    @Transactional
    public InventoryDTO createInventory(InventoryDTO inventoryDTO, Long productId)
    {
        var product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));

        var existingInventory = inventoryRepository.findByProduct(product)
                .orElseGet(()->{
                    var inventory = mapper.map(inventoryDTO,Inventory.class);

                    inventory.setProduct(product);

                    inventory.setLastUpdated(LocalDateTime.now());

                    product.setInventory(inventory);

                    return inventoryRepository.save(inventory);
                });

        return mapper.map(existingInventory,InventoryDTO.class);
    }

    @Override
    @Transactional
    @CachePut
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDetails)
    {
        var inventory = findInventoryById(id);

        inventory.setQuantity(inventoryDetails.getQuantity());

        inventory.setLastUpdated(LocalDateTime.now());

        return mapper.map(inventoryRepository.save(inventory),InventoryDTO.class);
    }

    @Override
    @Transactional
    @CacheEvict
    public InventoryDTO deleteInventory(Long id)
    {
        Inventory inventory = findInventoryById(id);

        inventory.getProduct().setInventory(null);

        inventoryRepository.delete(inventory);

        return mapper.map(inventory,InventoryDTO.class);
    }

    @Override
    public InventoryDTO getInventoryByProduct(Long productId)
    {
        var product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));

        var inventory = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory for product " , "id",productId));

        return mapper.map(inventory,InventoryDTO.class);
    }
}
