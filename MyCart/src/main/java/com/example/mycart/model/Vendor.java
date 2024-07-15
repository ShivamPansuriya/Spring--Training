package com.example.mycart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
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

    @Size(min = 10,max = 10)
    @Column(length = 10)
    private String phone;

    // will delete products associated to vendor automatically
    @OneToMany(mappedBy = "vendor",cascade = CascadeType.REMOVE)
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product)
    {
        products.add(product);
    }
}
