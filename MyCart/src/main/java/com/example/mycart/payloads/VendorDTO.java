package com.example.mycart.payloads;

import com.example.mycart.model.Category;
import com.example.mycart.model.Vendor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO
{
    private long id;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    @Email(message = "Please provide a valid email address")
    private String email;

    @Size(max = 10,message = "phone number size cannot be more tha 10 digits")
    private String phone;
}
