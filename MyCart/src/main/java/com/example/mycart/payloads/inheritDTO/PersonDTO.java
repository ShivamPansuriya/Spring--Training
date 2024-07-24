package com.example.mycart.payloads.inheritDTO;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PersonDTO extends NamedDTO
{
    private String address;

    @Email(message = "Please provide a valid email address")
    private String email;
}
