package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.InventoryDTO;
import com.example.mycart.repository.InventoryRepository;
import com.example.mycart.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<InventoryDTO> inventories() {
        var inventories = inventoryRepository.findAll();

        return inventories.stream().map(inventory-> mapper.map(inventory,InventoryDTO.class)).toList();
    }

    @Override
    public InventoryDTO getInventoryById(Long id) {
        var inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id" , id));
        return mapper.map(inventory,InventoryDTO.class);
    }

    @Override
    @Transactional
    public InventoryDTO createInventory(InventoryDTO inventoryDTO, Long productId)
    {
        var product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));

        var inventory = mapper.map(inventoryDTO,Inventory.class);

        inventory.setProduct(product);

        inventory.setLastUpdated(LocalDateTime.now());

        product.setInventory(inventory);

        return mapper.map(inventoryRepository.save(inventory),InventoryDTO.class);
    }

    @Override
    @Transactional
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDetails)
    {
        var inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id" , id));

        inventory.setQuantity(inventoryDetails.getQuantity());

        inventory.setLastUpdated(LocalDateTime.now());

        return mapper.map(inventoryRepository.save(inventory),InventoryDTO.class);
    }

    @Override
    @Transactional
    public InventoryDTO deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id" , id));

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
