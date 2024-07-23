package com.example.mycart.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Inventory implements BaseEntity<Inventory> {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
