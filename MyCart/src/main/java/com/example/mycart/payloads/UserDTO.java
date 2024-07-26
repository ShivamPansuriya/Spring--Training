package com.example.mycart.payloads;

import jakarta.validation.constraints.Email;
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
public class UserDTO extends NamedDTO
{
    @NotBlank(message = "address can't be empty!!")
    private String address;

    @Email(message = "Please provide a valid email address")
    private String email;

    private List<Long> orders;

    private Page<ReviewDTO> reviews;
}