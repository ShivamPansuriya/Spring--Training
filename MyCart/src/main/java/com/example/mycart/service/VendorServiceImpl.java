package com.example.mycart.service;

import com.example.mycart.exception.ApiException;
import com.example.mycart.model.Vendor;
import com.example.mycart.modelmapper.EntityMapper;
import com.example.mycart.payloads.VendorDTO;
import com.example.mycart.repository.VendorRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class VendorServiceImpl extends AbstractGenericService<Vendor, VendorDTO,Long> implements VendorService
{
    private final String[] COLUMN_HEADING = new String[]{"Product Name","Order Quantity","User Name","User Address","Remaining Quantity", "Total Earn", "Order Status"};

    private static final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Autowired
    VendorRepository repository;

    @Autowired
    EntityMapper<Vendor,VendorDTO> mapper;


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

    @Override
    protected JpaRepository<Vendor, Long> getRepository() {
        return repository;
    }

    @Override
    protected Class<Vendor> getEntityClass() {
        return Vendor.class;
    }

    @Override
    protected Class<VendorDTO> getDtoClass() {
        return VendorDTO.class;
    }
}
