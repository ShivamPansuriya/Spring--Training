package com.example.mycart.payloads;

import com.example.mycart.model.NamedEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO extends NamedDTO
{
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "address can't be empty!!")
    private String address;

    private String phone;

    private Page<String> products;
}
