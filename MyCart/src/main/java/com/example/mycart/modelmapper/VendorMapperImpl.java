package com.example.mycart.modelmapper;

import com.example.mycart.model.Vendor;
import com.example.mycart.payloads.VendorDTO;
import com.example.mycart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class VendorMapperImpl extends VendorMapper<Vendor,VendorDTO>
{
    @Autowired
    private ProductService service;

    @Override
    protected ProductService getProductService() {
        return service;
    }

    @Override
    public VendorDTO toDTO(Vendor entity, int pageNo)
    {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new VendorDTO());

        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setProducts(getProductName(entity.getId(),pageNo));
        return dto;
    }

    @Override
    public Vendor toEntity(VendorDTO dto) {
        if(dto == null)
        {
            return null;
        }
        var entity = mapToBaseEntity(dto,new Vendor());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        return entity;
    }
}
