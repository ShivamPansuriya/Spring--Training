package com.example.mycart.payloads;

import com.example.mycart.model.Product;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO
{
    private Long id;

    private ProductDTO product;

    @NotNull
    private int quantity;
}
