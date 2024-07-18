package com.example.mycart.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopSellingProductDTO
{
    private Long id;
    private String name;
    private int total_quantity;
    private double total_revenue;

}
