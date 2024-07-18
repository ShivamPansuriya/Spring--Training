package com.example.mycart.controller;

import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.ProductResponse;
import com.example.mycart.payloads.TopSellingProductDTO;
import com.example.mycart.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/products/")
@Slf4j
public class ProductController
{

    @Autowired
    private ProductService service;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/categories/{categoryId}/vendors/{vendorId}")
    public ResponseEntity<ProductDTO> createProduct(@PathVariable Long categoryId, @PathVariable Long vendorId,@RequestBody ProductDTO productDTO)
    {
        var response = service.addProduct(productDTO, categoryId,vendorId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts()
    {


        var response = service.getProducts(0L);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId)
    {
        var response = service.getProductByCategory(categoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String  keyword)
    {
        var response = service.getProductByKeyword(keyword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO)
    {
        var response = service.updateProduct(productId, productDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId)
    {
        var response = service.deleteProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/price-range")
    public ResponseEntity<ProductResponse> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice)
    {
        var products = service.findProductsByPriceRange(minPrice, maxPrice);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/top-selling-products")
    public ResponseEntity<List<TopSellingProductDTO>> getTopSellingProducts(@RequestParam(defaultValue = "10") int limit)
    {
        var topProducts = service.getTopSellingProducts(limit);

        return new ResponseEntity<>(topProducts, HttpStatus.OK);
    }
}
