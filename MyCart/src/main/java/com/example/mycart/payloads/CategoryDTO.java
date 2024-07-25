package com.example.mycart.payloads;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO extends NamedDTO
{
    private Long parentCategoryId;
    private Page<String> subCategoriesNames;
    private Page<String> productsNames;
}
