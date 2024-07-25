package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Product;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.TopSellingProductDTO;
import com.example.mycart.repository.CategoryRepository;
import com.example.mycart.repository.ProductRepository;
import com.example.mycart.repository.VendorRepository;
import com.example.mycart.utils.GenericSpecification;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl extends AbstractGenericService<Product,ProductDTO, Long> implements ProductService
{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product create(Product product, Long categoryId, Long vendorId)
    {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        var vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "vendorId", vendorId));

        return productRepository.findByNameAndCategoryAndVendor(product.getName(),category.getId(),vendor.getId())
                .orElseGet(()->{
                    log.info("creating new product");

                    var newProduct = modelMapper.map(product, Product.class);

                    newProduct.setCategoryId(category.getId());

                    newProduct.setVendorId(vendor.getId());

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
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        return productRepository.findByCategoryOrderByPriceAsc(category.getId(),PageRequest.of(pageNo,10));
    }

    @Override
    public Page<Product> getProductByKeyword(String keyword, int pageNo)
    {
        return productRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(pageNo,10));
    }

    @Override
    @Transactional
    public Product update(Long productId, ProductDTO productDTO) {
        var product = findById(productId);

        product.setName(productDTO.getName());

        product.setPrice(productDTO.getPrice());

        product.setDescription(productDTO.getDescription());

        return productRepository.save(product);
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
    protected JpaRepository<Product, Long> getRepository() {
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
