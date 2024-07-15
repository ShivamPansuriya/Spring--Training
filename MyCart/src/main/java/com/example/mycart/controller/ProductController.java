package com.example.mycart.controller;

import com.example.mycart.model.Product;
import com.example.mycart.payloads.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/product/")
public class ProductController {

    @PostMapping("/")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO)
    {

        return null;
    }
}
