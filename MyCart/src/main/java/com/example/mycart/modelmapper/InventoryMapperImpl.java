package com.example.mycart.modelmapper;

import com.example.mycart.model.Inventory;
import com.example.mycart.payloads.InventoryDTO;
import com.example.mycart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapperImpl extends InventoryMapper<Inventory,InventoryDTO>
{
    @Autowired
    private ProductService service;

    @Override
    public ProductService getProductService() {
        return service;
    }

    @Override
    public InventoryDTO toDTO(Inventory entity, int pageNo) {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new InventoryDTO());
        dto.setProductName(getProductName(entity.getProductId()));
        dto.setQuantity(entity.getQuantity());
        return dto;
    }

    @Override
    public Inventory toEntity(InventoryDTO dto) {
        if(dto == null)
        {
            return null;
        }
        var entity = new Inventory();
        entity.setQuantity(dto.getQuantity());
        return entity;
    }
}
