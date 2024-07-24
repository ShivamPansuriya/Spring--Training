package com.example.mycart.payloads.inheritDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends NamedDTO
{
    private BigDecimal price;
    private String categoryName;
    private String vendorName;
    private Long inventoryId;
    private List<String> reviews;
}