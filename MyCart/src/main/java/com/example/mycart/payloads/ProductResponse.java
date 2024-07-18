package com.example.mycart.payloads;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private List<ProductDTO> content;
}
