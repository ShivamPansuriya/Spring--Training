package com.example.mycart.service;

import com.example.mycart.payloads.VendorDTO;

import java.util.concurrent.Future;

public interface VendorService
{
    VendorDTO getVendorById(Long vendorId);

    VendorDTO addVendor(VendorDTO vendorDTO);

    VendorDTO updateVendor(Long vendorId,VendorDTO vendorDTO);

    VendorDTO deleteVendor(Long vendorId);

    byte[] generateAnalysis(Long vendorId);
}
