package com.example.mycart.service;


import com.example.mycart.model.Product;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId, Long vendorId);
    ProductResponse getProducts();
    ProductResponse getProductByCategory(Long categoryId);
    ProductResponse getProductByKeyword(String keyword);
    ProductDTO deleteProduct(Long productId);
    Product findByProductId(Long id);
}
