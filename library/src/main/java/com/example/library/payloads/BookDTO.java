package com.example.library.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO implements Serializable {

    @JsonIgnore
    private Long id;

    private String title;

    private String author;

    private String isbn;
}
