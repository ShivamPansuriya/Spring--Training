package com.example.mycart.service;

import com.example.mycart.model.Product;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.TopSellingProductDTO;
import com.example.mycart.repository.*;
import com.example.mycart.utils.GenericSpecification;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@Slf4j
public class ProductServiceImpl extends AbstractGenericService<Product,ProductDTO, Long> implements ProductService
{

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Product create(Product product, Long categoryId, Long vendorId)
    {
        return productRepository.findByNameAndCategoryAndVendor(product.getName(),categoryId,vendorId)
                .orElseGet(()->{
                    log.info("creating new product");

                    var newProduct = modelMapper.map(product, Product.class);

                    newProduct.setCategoryId(categoryId);

                    newProduct.setVendorId(vendorId);

                    return productRepository.save(newProduct);
                });
    }

    @Override
    public Page<Product> findProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice)
    {
        return productRepository.findProductsByPriceBetween(minPrice,maxPrice, PageRequest.of(0,10));
    }

    @Override
    public Page<Product> getProductByCategory(Long categoryId, int pageNo)
    {
        return productRepository.findByCategoryOrderByPriceAsc(categoryId,PageRequest.of(pageNo,10));
    }

    @Override
    public Page<Product> getProductByKeyword(String keyword, int pageNo)
    {
        return productRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(pageNo,10));
    }

    @Override
    public Page<TopSellingProductDTO> getTopSellingProducts(int limit, int pageNo)
    {
        var list = productRepository.findTopSellingProducts(limit, PageRequest.of(pageNo,10));

        return list.map(row -> new TopSellingProductDTO(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).intValue(),
                        ((Number) row[3]).doubleValue()));
    }

    @Override
    public Page<Product> findProductByVendor(Long vendorId, int pageNo) {
        return productRepository.findAll(GenericSpecification.getList("vendorId",vendorId),PageRequest.of(pageNo,10));
    }

    @Override
    protected SoftDeletesRepository<Product, Long> getRepository() {
        return productRepository;
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    protected Class<ProductDTO> getDtoClass() {
        return ProductDTO.class;
    }
}
