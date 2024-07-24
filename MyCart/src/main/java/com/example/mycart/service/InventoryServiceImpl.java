package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.inheritDTO.InventoryDTO;
import com.example.mycart.repository.InventoryRepository;
import com.example.mycart.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public class InventoryServiceImpl extends AbstractGenericService<Inventory, InventoryDTO, Long> implements InventoryService
{
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

//    @Override
//    public List<InventoryDTO> inventories()
//    {
//        var inventories = inventoryRepository.findAll();
//
//        return inventories.stream().map(inventory-> mapper.map(inventory,InventoryDTO.class)).toList();
//    }

    @Override
    public List<Inventory> findLowStockInventories(int threshold, Long vendorId)
    {
        return inventoryRepository.findLowStockInventories(threshold, vendorId);

    }

    public Inventory findInventoryById(Long id)
    {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id" , id));
    }

//    @Override
//    @Cacheable
//    public InventoryDTO getInventoryById(Long id)
//    {
//        var inventory = findInventoryById(id);
//
//        return mapper.map(inventory,InventoryDTO.class);
//    }

    @Override
    @Transactional
    public Inventory createInventory(InventoryDTO inventoryDTO, Long productId)
    {
        var product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));

        return inventoryRepository.findByProductId(product.getId())
                .orElseGet(()->{
                    var inventory = mapper.map(inventoryDTO,Inventory.class);

                    inventory.setProductId(product.getId());

                    inventory.setUpdatedTime(LocalDateTime.now());

                    var savedInventory = inventoryRepository.save(inventory);

                    product.setInventoryId(savedInventory.getId());

                    return savedInventory;
                });

    }

    @Override
    @Transactional
    @CachePut
    public Inventory update(Long id, InventoryDTO inventoryDetails)
    {
        var inventory = findInventoryById(id);

        inventory.setQuantity(inventoryDetails.getQuantity());

        inventory.setUpdatedTime(LocalDateTime.now());

        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    @CacheEvict
    public Inventory delete(Long id)
    {
        Inventory inventory = findInventoryById(id);

//        inventory.getProduct().setInventory(null);

        inventoryRepository.delete(inventory);

        return inventory;
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
