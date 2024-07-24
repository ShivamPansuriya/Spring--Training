package com.example.mycart.payloads.inheritDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class NamedDTO extends BaseDTO
{
    @NotBlank
    protected String name;

    protected String description;
}
