package com.example.mycart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class Category implements BaseEntity<Category> {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @NotBlank
//    @Column(nullable = false)
//    private String name;
//
//    private String description;
//
////    @ManyToOne
////    @JoinColumn(name = "parent_category_id")
//    private Long parentCategoryId;
//
////    @ToString.Exclude
////    @OneToMany(mappedBy = "parentCategory")
//    @Transient
//    private List<Long> subCategoriesId = new ArrayList<>();
//
////    @ToString.Exclude
////    @OneToMany(mappedBy = "category")
//    @Transient
//    private List<Long> productsId = new ArrayList<>();
//
//    public void addSubCategories(Category category)
//    {
//        subCategoriesId.add(category.getId());
//    }
//
//    @Override
//    public void setId(Long id) {
//        this.id = id;
//    }
//}

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends NamedEntity
{
    private Long parentCategoryId;

    @Transient
    private List<Long> subCategoriesId = new ArrayList<>();

    @Transient
    private List<Long> productsId = new ArrayList<>();

    public void addSubCategories(Category category) {
        subCategoriesId.add(category.getId());
    }
}
