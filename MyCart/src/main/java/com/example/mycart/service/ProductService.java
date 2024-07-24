package com.example.mycart.service;


import com.example.mycart.model.Product;
import com.example.mycart.payloads.inheritDTO.ProductDTO;
import com.example.mycart.payloads.ProductResponse;
import com.example.mycart.payloads.TopSellingProductDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService extends GenericService<Product,ProductDTO,Long>
{
    Product create(ProductDTO productDTO, Long categoryId, Long vendorId);

    Page<Product> getProductByCategory(Long categoryId, int pageNo);

    ProductResponse getProductByKeyword(String keyword);

    Product findByProductId(Long id);

    Page<Product> findProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<TopSellingProductDTO> getTopSellingProducts(int limit);

    Page<Product> findProductByVendor(Long vendorId, int pageNo);

}
