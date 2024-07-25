package com.example.mycart.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class NamedDTO extends BaseDTO
{
    @NotBlank(message = "address can't be empty!!")
    protected String name;

    protected String description;
}
