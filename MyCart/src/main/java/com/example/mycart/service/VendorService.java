package com.example.mycart.service;

import com.example.mycart.model.Vendor;
import com.example.mycart.payloads.inheritDTO.VendorDTO;


public interface VendorService extends GenericService<Vendor,VendorDTO,Long>
{
//    VendorDTO getVendorById(Long vendorId);
//
//    VendorDTO addVendor(VendorDTO vendorDTO);
//
//    VendorDTO updateVendor(Long vendorId,VendorDTO vendorDTO);
//
//    VendorDTO deleteVendor(Long vendorId);

    byte[] generateAnalysis(Long vendorId);
}
