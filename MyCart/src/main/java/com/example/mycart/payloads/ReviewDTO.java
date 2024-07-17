package com.example.mycart.payloads;

import com.example.mycart.utils.Ratings;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO
{
    private Long id;
    private Ratings ratings;
    private LocalDateTime reviewDate;
    private String comment;
}
