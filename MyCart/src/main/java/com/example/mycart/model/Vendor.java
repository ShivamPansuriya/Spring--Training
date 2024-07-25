package com.example.mycart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;


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
}
