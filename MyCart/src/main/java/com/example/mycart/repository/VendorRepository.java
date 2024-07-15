package com.example.mycart.repository;

import com.example.mycart.model.User;
import com.example.mycart.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {
}
