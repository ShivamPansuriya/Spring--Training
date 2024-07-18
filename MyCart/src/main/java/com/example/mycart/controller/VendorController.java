package com.example.mycart.controller;

import com.example.mycart.payloads.VendorDTO;
import com.example.mycart.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/vendor/")
public class VendorController {

    @Autowired
    VendorService service;

    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> getVendor(@PathVariable Long vendorId)
    {
        var vendor = service.getVendorById(vendorId);

        return new ResponseEntity<>(vendor, HttpStatus.CREATED);
    }

    @PostMapping("/")
    public ResponseEntity<VendorDTO> addVendor(@RequestBody VendorDTO vendorDTO)
    {
        var vendor = service.addVendor(vendorDTO);

        return new ResponseEntity<>(vendor, HttpStatus.CREATED);
    }

    @PutMapping("/{VendorId}")
    public ResponseEntity<VendorDTO> updateVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long VendorId)
    {
        var updatedVendor = service.updateVendor(VendorId,vendorDTO);

        return new ResponseEntity<>(updatedVendor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{VendorId}")
    public ResponseEntity<VendorDTO> deleteVendor(@PathVariable Long VendorId)
    {
        var deletedVendor = service.deleteVendor(VendorId);

        return new ResponseEntity<>(deletedVendor, HttpStatus.CREATED);
    }

    @Async("threadPoolTaskExecutor")
    @GetMapping("/{vendorId}/download")
    public Future<ResponseEntity<byte[]>> downloadExcel(@PathVariable Long vendorId) {

            var excelContent = service.generateAnalysis(vendorId);

            System.out.println(Thread.currentThread().getName());

            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "analysis.xlsx");

            return new AsyncResult<>(ResponseEntity.ok()
                .headers(headers)
                .body(excelContent));
    }

}
