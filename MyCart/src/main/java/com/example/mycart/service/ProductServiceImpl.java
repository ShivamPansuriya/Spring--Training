package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Product;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.ProductResponse;
import com.example.mycart.payloads.TopSellingProductDTO;
import com.example.mycart.repository.CategoryRepository;
import com.example.mycart.repository.ProductRepository;
import com.example.mycart.repository.VendorRepository;
import jakarta.persistence.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService
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
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId, Long vendorId)
    {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        var vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "vendorId", vendorId));

        var product = productRepository.findByNameAndCategoryAndVendor(productDTO.getName(),category,vendor)
                .orElseGet(()->{
                    log.info("creating new product");

                    var newProduct = modelMapper.map(productDTO, Product.class);

                    newProduct.setCategory(category);

                    newProduct.setVendor(vendor);

                    return productRepository.save(newProduct);
                });

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductResponse getProducts(Long dummy)
    {
        var products = productRepository.findAll();

        var productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        var productResponse = new ProductResponse();

        productResponse.setContent(productDTOs);

        return productResponse;
    }

    @Override
    public Product findByProductId(Long id)
    {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product","id",id));
    }

    @Override
    public ProductResponse findProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice)
    {
        var products = productRepository.findByPriceBetween(minPrice,maxPrice);

        var productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        var productResponse = new ProductResponse();

        productResponse.setContent(productDTOs);

        return productResponse;
    }

    @Override
    public ProductResponse getProductByCategory(Long categoryId)
    {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        var products = productRepository.findByCategoryOrderByPriceAsc(category);

        var productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        var productResponse = new ProductResponse();

        productResponse.setContent(productDTOs);

        return productResponse;
    }

    @Override
    public ProductResponse getProductByKeyword(String keyword)
    {
        var products = productRepository.findByNameContainingIgnoreCase(keyword);

        var productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        var productResponse = new ProductResponse();

        productResponse.setContent(productDTOs);

        return productResponse;
    }

    @Override
    @Transactional
    public ProductDTO deleteProduct(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.delete(product);

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        var product = findByProductId(productId);

        product.setName(productDTO.getName());

        product.setPrice(productDTO.getPrice());

        product.setDescription(productDTO.getDescription());

        var updatedProduct = productRepository.save(product);

        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public List<TopSellingProductDTO> getTopSellingProducts(int limit)
    {
        var list = productRepository.findTopSellingProducts(limit);

        return list.stream()
                .map(row -> new TopSellingProductDTO(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).intValue(),
                        ((Number) row[3]).doubleValue()))
                .toList();
    }

}
