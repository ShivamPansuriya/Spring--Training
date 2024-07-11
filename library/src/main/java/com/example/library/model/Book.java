package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private transient String title1;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

//    will create column and start with default value which is provided if not set by us.
//    @ColumnDefault(value = "TRUE")
//    private boolean a;
//
//    use transient key-word to exclude particular filed from adding to table.(also exclude from serialization)

    @ManyToMany(mappedBy = "booksBorrowed")
    private List<Member> borrowingMember;
}
