package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
//    @UniqueElements
    @Column(unique = true)
    private String memberId;

    @OneToMany(mappedBy = "member")
    private List<Borrowing> borrowingList;


    @ManyToMany()
    @JoinTable(name = "member_book",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> booksBorrowed;

    public void addToBorrowedBook(Book book)
    {
        booksBorrowed.add(book);
    }

    public void removeFromBorrowedBook(Book book)
    {
        booksBorrowed.remove(book);
    }

}
