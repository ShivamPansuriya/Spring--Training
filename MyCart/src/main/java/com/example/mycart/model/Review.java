package com.example.mycart.model;

import com.example.mycart.utils.Ratings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity<Long>
{
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Ratings ratings;

    @Column(length = 500)
    private String comment;
}