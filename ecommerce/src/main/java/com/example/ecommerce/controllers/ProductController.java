package com.example.ecommerce.controllers;

import com.example.ecommerce.paylods.ProductDTO;
import com.example.ecommerce.paylods.ProductResponse;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> createProduct(@PathVariable Long categoryId, @RequestBody ProductDTO productDTO)
    {
        var response = productService.addProduct(productDTO, categoryId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    @Cacheable(value = "product", key = "productId")
    public ResponseEntity<ProductResponse> getAllProducts()
    {
        var response = productService.getProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId)
    {
        var response = productService.getProductByCategory(categoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String  keyword)
    {
        var response = productService.getProductByKeyword(keyword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId)
    {
        var response = productService.deleteProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
