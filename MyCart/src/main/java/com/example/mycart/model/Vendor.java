package com.example.mycart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor extends NamedEntity
{
    private String address;

    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(length = 10)
    private String phone;

    @Transient
    private ArrayList<Long> productsId = new ArrayList<>();
}
