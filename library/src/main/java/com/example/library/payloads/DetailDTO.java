package com.example.library.payloads;

import com.example.library.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailDTO {
    private String name;
    private String memberId;
    private List<BookDTO> booksBorrowed;
}
