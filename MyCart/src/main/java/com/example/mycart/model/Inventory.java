package com.example.mycart.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Inventory extends BaseEntity<Long>
{

    @Column(nullable = false)
    protected int quantity;

    @Column(nullable = false)
    private Long productId;

}
