package com.example.mycart.controller;

import com.example.mycart.payloads.ApiResponse;
import com.example.mycart.payloads.VendorDTO;
import com.example.mycart.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/")
public class VendorController {

    @Autowired
    VendorService service;

    @PostMapping("/")
    public ResponseEntity<VendorDTO> addVendor(@RequestBody VendorDTO vendorDTO)
    {
        return new ResponseEntity<>(service.addVendor(vendorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{VendorId}")
    public ResponseEntity<VendorDTO> updateVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long VendorId)
    {
        return new ResponseEntity<>(service.updateVendor(vendorDTO,VendorId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{VendorId}")
    public ResponseEntity<ApiResponse> deleteVendor(@PathVariable Long VendorId)
    {
        service.deleteVendor(VendorId);

        return new ResponseEntity<>(new ApiResponse("Delete successful", true), HttpStatus.CREATED);
    }
}
