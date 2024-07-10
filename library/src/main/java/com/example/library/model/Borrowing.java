package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "borrowing")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @NotBlank
    private Date borrowDate;

    private Date returnDate;
}
