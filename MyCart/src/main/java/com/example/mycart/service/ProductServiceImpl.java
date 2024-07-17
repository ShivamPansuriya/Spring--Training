package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Product;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.ProductResponse;
import com.example.mycart.repository.CategoryRepository;
import com.example.mycart.repository.ProductRepository;
import com.example.mycart.repository.VendorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId, Long vendorId) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        var vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "vendorId", vendorId));

        var product = modelMapper.map(productDTO, Product.class);

        product.setCategory(category);

        product.setVendor(vendor);

        category.addProducts(product);

        vendor.addProduct(product);

        var savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getProducts() {
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
    public ProductResponse getProductByCategory(Long categoryId) {
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
}
