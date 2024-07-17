package com.example.mycart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    @Email(message = "Please provide a valid email address")
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}

/*
SELECT p.name AS productName, oi.quantity AS orderQuantity, u.name AS userName, u.address AS userAddress, i.quantity AS remainingQuantity,
       (oi.quantity * p.price) AS totalEarn, o.status AS orderStatus
FROM Order o
JOIN o.orderItems oi
JOIN oi.product p
JOIN p.vendor v
JOIN o.user u
JOIN p.inventory i
WHERE v.id = :vendorId


SELECT p.name as product_name, ot.quantity as order_quantity, u.name as user_name, u.address as user_address, i.quantity as remaining_quantity,
		(ot.quantity * p.price) as total_earn
                     FROM orders o
                     JOIN order_items ot ON ot.order_id = o.id
                     JOIN product p ON ot.product_id = p.id
                     JOIN vendor v On p.vendor_id = v.id
					 JOIN users u ON o.user_id = u.id
					 JOIN inventory i ON i.product_id = p.id
					 where v.id = 1;
 */