package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;

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

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;
}
