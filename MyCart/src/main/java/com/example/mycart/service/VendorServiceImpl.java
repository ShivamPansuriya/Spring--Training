package com.example.mycart.service;

import com.example.mycart.exception.ApiException;
import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Vendor;
import com.example.mycart.payloads.VendorDTO;
import com.example.mycart.repository.VendorRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;

@Service
@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public class VendorServiceImpl implements VendorService
{

    private final String[] COLUMN_HEADING = new String[]{"Product Name","Order Quantity","User Name","User Address","Remaining Quantity", "Total Earn", "Order Status"};

    private static final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);
    @Autowired
    VendorRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    @Cacheable
    public VendorDTO getVendorById(Long vendorId)
    {
        var vendor = findVendorById(vendorId);

        return mapper.map(vendor, VendorDTO.class);
    }

    @Override
    public VendorDTO addVendor(VendorDTO vendorDTO)
    {
        var vendor = mapper.map(vendorDTO, Vendor.class);

        var savedVendor = repository.save(vendor);

        return mapper.map(savedVendor, VendorDTO.class);
    }


    public Vendor findVendorById(Long vendorId)
    {
        return repository.findById(vendorId)
                .orElseThrow(()->new ResourceNotFoundException("Vendor","id",vendorId));
    }

    @Override
    @CachePut
    @Transactional
    public VendorDTO updateVendor(Long vendorId,VendorDTO vendorDTO)
    {
        var vendor = findVendorById(vendorId);

        vendor.setDescription(vendorDTO.getDescription());

        vendor.setEmail(vendorDTO.getEmail());

        vendor.setName(vendorDTO.getName());

        vendor.setPhone(vendorDTO.getPhone());

        return mapper.map(vendor, VendorDTO.class);
    }

    @Override
    @CacheEvict
    @Transactional
    public VendorDTO deleteVendor(Long vendorId)
    {
        var vendor = findVendorById(vendorId);

        repository.delete(vendor);

        return mapper.map(vendor, VendorDTO.class);
    }

    public byte[] generateAnalysis(Long vendorId)
    {
        try (Workbook workbook = new XSSFWorkbook())
        {
            Sheet sheet = workbook.createSheet("account analysis");

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < COLUMN_HEADING.length; i++)
            {
                Cell cell = headerRow.createCell(i);

                cell.setCellValue(COLUMN_HEADING[i]);
            }

            var result = repository.findAnalysis(vendorId);

            int rowNum = 0;

            while (++rowNum<=result.size())
            {
                Row row = sheet.createRow(rowNum);
                for(int i=0; i< COLUMN_HEADING.length; ++i)
                {
                    var cell = row.createCell(i);
                    cell.setCellValue(result.get(rowNum-1)[i].toString());
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            workbook.write(outputStream);

            return outputStream.toByteArray();
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ApiException("Unable to generate response");
        }
    }
}
