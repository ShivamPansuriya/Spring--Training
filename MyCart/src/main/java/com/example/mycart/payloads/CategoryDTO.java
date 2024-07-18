package com.example.mycart.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO
{
    private long id;

    @NotBlank
    private String name;

    private String description;

    private Long parentCategoryId;
}
