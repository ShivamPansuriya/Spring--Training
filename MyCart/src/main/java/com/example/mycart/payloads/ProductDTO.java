package com.example.mycart.payloads;

import com.example.mycart.model.Category;
import com.example.mycart.model.Inventory;
import com.example.mycart.model.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO
{
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private CategoryDTO category;
    private VendorDTO vendor;
}
