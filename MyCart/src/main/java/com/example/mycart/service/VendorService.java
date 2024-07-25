package com.example.mycart.service;

import com.example.mycart.model.Vendor;
import com.example.mycart.payloads.VendorDTO;


public interface VendorService extends GenericService<Vendor,VendorDTO,Long>
{
    byte[] generateAnalysis(Long vendorId);
}
