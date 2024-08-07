package com.example.library.payloads;

import com.example.library.model.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDTO {

    @JsonIgnore
    private Long id;

    private String name;

    private String memberId;

}
