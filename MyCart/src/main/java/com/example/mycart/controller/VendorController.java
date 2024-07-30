package com.example.mycart.controller;

import com.example.mycart.model.Vendor;
import com.example.mycart.modelmapper.EntityMapper;
import com.example.mycart.modelmapper.VendorMapper;
import com.example.mycart.payloads.VendorDTO;
import com.example.mycart.service.VendorService;
import com.example.mycart.service.GenericService;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/vendor/")
public class VendorController extends AbstractGenericController<Vendor,VendorDTO,Long>
{
    final
    VendorService service;

    private final VendorMapper<Vendor,VendorDTO> mapper;

    public VendorController(VendorService service, VendorMapper<Vendor, VendorDTO> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Async("threadPoolTaskExecutor")
    @GetMapping("/{vendorId}/download")
    public Future<ResponseEntity<byte[]>> downloadExcel(@PathVariable Long vendorId) {

            var excelContent = service.generateAnalysis(vendorId);

            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "analysis.xlsx");

            return new AsyncResult<>(ResponseEntity.ok()
                .headers(headers)
                .body(excelContent));
    }

    @Override
    protected EntityMapper<Vendor, VendorDTO> getMapper() {
        return mapper;
    }

    @Override
    protected GenericService<Vendor,VendorDTO, Long> getService() {
        return service;
    }
}
