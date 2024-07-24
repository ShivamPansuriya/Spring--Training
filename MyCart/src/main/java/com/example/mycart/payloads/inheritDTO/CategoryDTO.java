package com.example.mycart.payloads.inheritDTO;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

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
