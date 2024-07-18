package com.example.mycart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String description;

    @NotBlank
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false)
    private String email;

    @Column(length = 10)
    private String phone;

    @OneToMany(mappedBy = "vendor",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product)
    {
        products.add(product);
    }
}
