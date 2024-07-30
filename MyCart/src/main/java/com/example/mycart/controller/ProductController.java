package com.example.mycart.controller;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Product;
import com.example.mycart.modelmapper.EntityMapper;
import com.example.mycart.modelmapper.ProductMapper;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.TopSellingProductDTO;
import com.example.mycart.service.ProductService;
import com.example.mycart.service.GenericService;
import com.example.mycart.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/products/")
@Slf4j
public class ProductController extends AbstractGenericController<Product,ProductDTO,Long>
{

    private final ProductService service;
    private final ProductMapper<Product,ProductDTO> mapper;

    private final Validator validator;

    public ProductController(ProductService service, ProductMapper<Product, ProductDTO> mapper, Validator validator) {
        this.service = service;
        this.mapper = mapper;
        this.validator = validator;
    }

    @PostMapping("/categories/{categoryId}/vendors/{vendorId}")
    public ResponseEntity<ProductDTO> createProduct(@PathVariable Long categoryId, @PathVariable Long vendorId,@RequestBody ProductDTO productDTO)
    {
        if(validator.validateCategory(categoryId))
        {
            throw new ResourceNotFoundException("category","id",categoryId);
        }
        if(validator.validateVendor(vendorId))
        {
            throw new ResourceNotFoundException("Vendor","id",vendorId);
        }
        var response = service.create(mapper.toEntity(productDTO), categoryId,vendorId);

        return new ResponseEntity<>(mapper.toDTO(response,0), HttpStatus.CREATED);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId,@RequestParam(defaultValue = "0") int pageNo)
    {
        if(validator.validateCategory(categoryId))
        {
            throw new ResourceNotFoundException("category","id",categoryId);
        }
        var products = service.getProductByCategory(categoryId,pageNo);

        var response = products.map(product ->mapper.toDTO(product,pageNo));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<Page<ProductDTO>> getProductsByKeyword(@PathVariable String  keyword, @RequestParam(defaultValue = "0", required = false) int pageNo)
    {
        var response = service.getProductByKeyword(keyword, pageNo);

        return new ResponseEntity<>(response.map(product->mapper.toDTO(product,pageNo)), HttpStatus.OK);
    }


    @GetMapping("/price-range")
    public ResponseEntity<Page<Product>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice)
    {
        var products = service.findProductsByPriceRange(minPrice, maxPrice);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/top-selling-products")
    public ResponseEntity<Page<TopSellingProductDTO>> getTopSellingProducts(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0",required = false) int pageNo)
    {
        var topProducts = service.getTopSellingProducts(limit, pageNo);

        return new ResponseEntity<>(topProducts, HttpStatus.OK);
    }

    @Override
    protected EntityMapper<Product, ProductDTO> getMapper() {
        return mapper;
    }

    @Override
    protected GenericService<Product,ProductDTO, Long> getService() {
        return service;
    }
}
