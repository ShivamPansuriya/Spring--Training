package com.example.mycart.controller;

import com.example.mycart.payloads.VendorDTO;
import com.example.mycart.service.VendorService;
import com.example.mycart.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/vendor/")
public class VendorController extends AbstractGenericController<VendorDTO,Long>
{
    @Autowired
    VendorService service;

//    @PutMapping("/{vendorId}")
//    public ResponseEntity<VendorDTO> updateVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long vendorId)
//    {
////        vendorDTO.setId(vendorId);
//
//        var updatedVendor = service.update(vendorId,vendorDTO);
//
//        return new ResponseEntity<>(updatedVendor, HttpStatus.CREATED);
//    }

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

    @Override
    protected GenericService<VendorDTO, Long> getService() {
        return service;
    }
}
