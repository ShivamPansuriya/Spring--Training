package com.example.mycart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity<ID extends Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected ID id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    protected LocalDateTime updatedTime;

    protected  boolean deleted;
}
