package com.example.mycart.payloads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class InventoryDTO extends BaseDTO
{
    private int quantity;

    private String productName;
}