package com.example.mycart.service;


import com.example.mycart.model.Product;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.TopSellingProductDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService extends GenericService<Product,ProductDTO,Long>
{
    Product create(Product product, Long categoryId, Long vendorId);

    Page<Product> getProductByCategory(Long categoryId, int pageNo);

    Page<Product> getProductByKeyword(String keyword, int pageNo);

    Page<Product> findProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    Page<TopSellingProductDTO> getTopSellingProducts(int limit, int pageNo);

    Page<Product> findProductByVendor(Long vendorId, int pageNo);

}
