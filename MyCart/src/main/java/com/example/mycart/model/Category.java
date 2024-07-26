package com.example.mycart.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends NamedEntity
{
    private Long parentCategoryId;

}
