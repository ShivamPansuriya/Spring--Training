package com.example.mycart.payloads;

import com.example.mycart.model.Category;
import com.example.mycart.model.Inventory;
import com.example.mycart.model.Vendor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO
{
    private Long id =0L;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;

    private CategoryDTO category;

    private VendorDTO vendor;

    private List<ReviewDTO> reviews;
}
