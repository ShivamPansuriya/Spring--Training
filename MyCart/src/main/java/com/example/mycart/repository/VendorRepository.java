package com.example.mycart.repository;

import com.example.mycart.model.Vendor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends BaseRepository<Vendor,Long>
{
    @Query(value = "SELECT p.name AS productName, oi.quantity AS orderQuantity, u.name AS userName, u.address AS userAddress, i.quantity AS remainingQuantity,(oi.quantity * p.price) AS totalEarn, o.status AS orderStatus FROM Order o JOIN OrderItems oi on oi.orderId = o.id JOIN Product p on oi.productId = p.id JOIN Inventory i on p.id = i.productId JOIN Vendor v on p.vendorId = v.id JOIN User u on o.userId = u.id WHERE v.id = :vendorId")
    List<Object[]> findAnalysis(@Param("vendorId") Long vendorId);
}
