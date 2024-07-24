package com.example.mycart.payloads.inheritDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO
{
    protected Long id;

    protected LocalDateTime createdTime;

    protected LocalDateTime updatedTime;
}
