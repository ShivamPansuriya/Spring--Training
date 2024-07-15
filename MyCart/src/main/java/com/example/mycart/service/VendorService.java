package com.example.mycart.service;

import com.example.mycart.payloads.VendorDTO;

public interface VendorService
{
    VendorDTO addVendor(VendorDTO vendorDTO);

    VendorDTO updateVendor(VendorDTO vendorDTO,Long vendorId);

    void deleteVendor(Long vendorId);
}
