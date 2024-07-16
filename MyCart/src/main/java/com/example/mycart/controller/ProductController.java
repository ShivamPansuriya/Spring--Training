package com.example.mycart.controller;

import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.ProductResponse;
import com.example.mycart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products/")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/categories/{categoryId}/vendors/{vendorId}")
    public ResponseEntity<ProductDTO> createProduct(@PathVariable Long categoryId, @PathVariable Long vendorId,@RequestBody ProductDTO productDTO)
    {
        var response = service.addProduct(productDTO, categoryId,vendorId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<ProductResponse> getAllProducts()
    {
        var response = service.getProducts();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId)
    {
        var response = service.getProductByCategory(categoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String  keyword)
    {
        var response = service.getProductByKeyword(keyword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId)
    {
        var response = service.deleteProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
