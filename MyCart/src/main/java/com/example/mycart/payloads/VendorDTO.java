package com.example.mycart.payloads;

import com.example.mycart.model.Category;
import com.example.mycart.model.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {
    private long id;
    private String name;
    private String description;
    private String email;
    private String phone;
}
