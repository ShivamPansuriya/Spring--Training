package com.example.ecommerce.service;


import com.example.ecommerce.entity.Product;
import com.example.ecommerce.paylods.ProductDTO;
import com.example.ecommerce.paylods.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);
    ProductResponse getProducts();
    ProductResponse getProductByCategory(Long categoryId);
    ProductResponse getProductByKeyword(String keyword);
    ProductDTO deleteProduct(Long productId);
}
