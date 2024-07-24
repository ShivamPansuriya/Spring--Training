package com.example.mycart.controller;

import com.example.mycart.model.Product;
import com.example.mycart.payloads.inheritDTO.ProductDTO;
import com.example.mycart.payloads.ProductResponse;
import com.example.mycart.payloads.TopSellingProductDTO;
import com.example.mycart.service.ProductService;
import com.example.mycart.service.GenericService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/products/")
@Slf4j
public class ProductController extends AbstractGenericController<Product,ProductDTO,Long>
{

    @Autowired
    private ProductService service;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/categories/{categoryId}/vendors/{vendorId}")
    public ResponseEntity<ProductDTO> createProduct(@PathVariable Long categoryId, @PathVariable Long vendorId,@RequestBody ProductDTO productDTO)
    {
        var response = service.create(productDTO, categoryId,vendorId);

        return new ResponseEntity<>(mapper.map(response,0), HttpStatus.CREATED);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId,@RequestParam(defaultValue = "0") int pageNo)
    {
        var products = service.getProductByCategory(categoryId,pageNo);

        var response = products.map(product ->mapper.map(product,pageNo));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String  keyword)
    {
        var response = service.getProductByKeyword(keyword);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<List<TopSellingProductDTO>> getTopSellingProducts(@RequestParam(defaultValue = "10") int limit)
    {
        var topProducts = service.getTopSellingProducts(limit);

        return new ResponseEntity<>(topProducts, HttpStatus.OK);
    }

    @Override
    protected GenericService<Product,ProductDTO, Long> getService() {
        return service;
    }
}
