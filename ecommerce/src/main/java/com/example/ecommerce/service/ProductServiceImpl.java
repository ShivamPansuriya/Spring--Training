package com.example.ecommerce.service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exceptions.APIException;
import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.paylods.ProductDTO;
import com.example.ecommerce.paylods.ProductResponse;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        var productInDb = productRepository.findByProductNameLikeIgnoreCase(productDTO.getProductName());
        if (productInDb != null) {
            throw new APIException("Product with name " + productDTO.getProductName() +" already exists");
        }
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        var product = modelMapper.map(productDTO, Product.class);

        product.setCategory(category);
        product.setImage("defaultImage.png");
        double price = product.getPrice() - ((product.getDiscount()*product.getPrice())/100);
        product.setPrice(price);

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
    public ProductResponse getProductByKeyword(String keyword) {
        var products = productRepository.findByProductNameContainingIgnoreCase(keyword);
        var productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        var productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        return productResponse;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.delete(product);

        return modelMapper.map(product, ProductDTO.class);
    }
}
