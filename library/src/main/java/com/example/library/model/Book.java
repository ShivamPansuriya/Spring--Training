package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Check;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
@Check( constraints = "CASE WHEN isbn IS NOT NULL THEN LENGTH(isbn) = 13 ELSE true END") // this will throw error if isbn size is not 13
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

//    will create column and start with default value which is provided if not set by us.
//    @ColumnDefault(value = "TRUE")
//    private boolean a;
//
//    use transient key-word to exclude particular filed from adding to table.(also exclude from serialization)

    @ManyToMany(mappedBy = "booksBorrowed")
    private List<Member> borrowingMember;

    public void addToBorrowingMember(Member member)
    {
        borrowingMember.add(member);
    }

    public void removeFromBorrowingMember(Member member)
    {
        borrowingMember.remove(member);
    }
}
