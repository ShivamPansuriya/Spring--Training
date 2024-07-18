package com.example.mycart.repository;

import com.example.mycart.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long>
{
    @Query(value = "SELECT p.name AS productName, oi.quantity AS orderQuantity, u.name AS userName, u.address AS userAddress, p.inventory.quantity AS remainingQuantity,(oi.quantity * p.price) AS totalEarn, o.status AS orderStatus FROM Order o JOIN o.orderItems oi JOIN oi.product p JOIN p.vendor v JOIN o.user u WHERE v.id = :vendorId")
    List<Object[]> findAnalysis(@Param("vendorId") Long vendorId);
}
