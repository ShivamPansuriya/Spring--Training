package com.example.mycart.service;

import com.example.mycart.payloads.VendorDTO;


public interface VendorService extends GenericService<VendorDTO,Long>
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
