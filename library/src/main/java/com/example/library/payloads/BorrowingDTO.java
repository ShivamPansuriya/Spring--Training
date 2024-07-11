package com.example.library.payloads;

import com.example.library.model.Book;
import com.example.library.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BorrowingDTO {

    @JsonIgnore
    private Long id;

    private Date borrowedDate;

    private Date returnDate;
}
