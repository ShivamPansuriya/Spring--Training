package com.example.mycart.payloads.inheritDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO extends PersonDTO
{
    @NotBlank
    private String address;

    private String phone;

    private Page<String> products;
}
