package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Vendor;
import com.example.mycart.payloads.VendorDTO;
import com.example.mycart.repository.VendorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VendorServiceImpl implements VendorService{

    @Autowired
    VendorRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public VendorDTO addVendor(VendorDTO vendorDTO)
    {
        var vendor = mapper.map(vendorDTO, Vendor.class);

        var savedVendor = repository.save(vendor);

        return mapper.map(savedVendor, VendorDTO.class);
    }


    public Vendor getVendorById(Long vendorId)
    {
        var optional = repository.findById(vendorId);

        if(optional.isEmpty())
        {
            throw new ResourceNotFoundException("Vendor","id",vendorId);
        }

        return optional.get();
    }

    @Override
    public VendorDTO updateVendor(VendorDTO vendorDTO, Long vendorId)
    {
        var vendor = getVendorById(vendorId);

        vendor.setDescription(vendorDTO.getDescription());

        vendor.setEmail(vendorDTO.getEmail());

        vendor.setName(vendorDTO.getName());

        vendor.setPhone(vendorDTO.getPhone());

        return mapper.map(vendor, VendorDTO.class);
    }

    @Override
    public void deleteVendor(Long vendorId)
    {
        var vendor = getVendorById(vendorId);

        repository.delete(vendor);
    }
}
