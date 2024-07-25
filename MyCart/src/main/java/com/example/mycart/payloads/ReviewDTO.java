package com.example.mycart.payloads;


import com.example.mycart.utils.Ratings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO extends BaseDTO
{
    private String userName;

    private String productName;

    private Ratings rating;

    private LocalDateTime reviewDate;

    private String comment;
}
