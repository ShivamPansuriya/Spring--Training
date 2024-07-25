package com.example.mycart.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends NamedDTO
{
    private BigDecimal price;
    private String categoryName;
    private String vendorName;
    private Page<ReviewDTO> reviews;
}