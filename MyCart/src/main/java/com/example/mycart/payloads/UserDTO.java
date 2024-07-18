package com.example.mycart.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO
{
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    @Email(message = "Please provide a valid email address")
    private String email;
}
