package com.example.mycart.service;


import com.example.mycart.model.Product;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.ProductResponse;
import com.example.mycart.payloads.TopSellingProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService
{
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId, Long vendorId);

    ProductResponse getProducts(Long dummy);

    ProductResponse getProductByCategory(Long categoryId);

    ProductResponse getProductByKeyword(String keyword);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    Product findByProductId(Long id);

    ProductResponse findProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<TopSellingProductDTO> getTopSellingProducts(int limit);
}
