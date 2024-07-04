package com.example.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "street name must be of atleast 5 character")
    private String street;

    @NotBlank
    @Size(min = 4, message = "building name must be of atleast 4 character")
    private String building;

    @NotBlank
    @Size(min = 2, message = "city name must be of atleast 2 character")
    private String city;

    @NotBlank
    @Size(min = 5, message = "country name must be of atleast 5 character")
    private String country;

    @NotBlank
    @Size(min = 6, message = "Pincode name must be of atleast 4 character")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();
}
