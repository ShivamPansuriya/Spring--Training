package com.example.mycart.model;

import com.example.mycart.utils.Ratings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Review implements BaseEntity<Review> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Ratings ratings;

    @Column(nullable = false)
    private LocalDateTime reviewDate;

    @NotBlank
    @Column(length = 500)
    private String comment;

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
