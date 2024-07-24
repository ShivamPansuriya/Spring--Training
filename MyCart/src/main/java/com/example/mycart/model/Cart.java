package com.example.mycart.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity<Long>
{
    @Column(nullable = false)
    private Long userId;

    @Transient
    private List<Long> cartItemsId = new ArrayList<>();
}

