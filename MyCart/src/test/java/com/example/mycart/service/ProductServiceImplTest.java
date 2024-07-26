package com.example.mycart.service;

import com.example.mycart.model.Product;
import com.example.mycart.model.Category;
import com.example.mycart.model.Vendor;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.TopSellingProductDTO;
import com.example.mycart.repository.ProductRepository;
import com.example.mycart.repository.CategoryRepository;
import com.example.mycart.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private VendorRepository vendorRepository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    private ProductDTO productDTO;

    private Category category;

    private Vendor vendor;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setDescription("Test Description");
        product.setCategoryId(1L);
        product.setVendorId(1L);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setPrice(BigDecimal.valueOf(100));
        productDTO.setDescription("Test Description");

        category = new Category();
        category.setId(1L);

        vendor = new Vendor();
        vendor.setId(1L);
    }

    @Test
    void testCreate() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(productRepository.findByNameAndCategoryAndVendor(anyString(), anyLong(), anyLong())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.create(product, 1L, 1L);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(vendorRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findByNameAndCategoryAndVendor(anyString(), anyLong(), anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testFindProductsByPriceRange() {
        List<Product> products = Arrays.asList(product);
        Page<Product> page = new PageImpl<>(products);
        when(productRepository.findProductsByPriceBetween(any(BigDecimal.class), any(BigDecimal.class), any(PageRequest.class))).thenReturn(page);

        Page<Product> result = productService.findProductsByPriceRange(BigDecimal.valueOf(50), BigDecimal.valueOf(150));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(product.getId(), result.getContent().get(0).getId());
    }

    @Test
    void testGetProductByCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        List<Product> products = Arrays.asList(product);
        Page<Product> page = new PageImpl<>(products);
        when(productRepository.findByCategoryOrderByPriceAsc(anyLong(), any(PageRequest.class))).thenReturn(page);

        Page<Product> result = productService.getProductByCategory(1L, 0);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(product.getId(), result.getContent().get(0).getId());
    }

    @Test
    void testGetProductByKeyword() {
        List<Product> products = Arrays.asList(product);
        Page<Product> page = new PageImpl<>(products);
        when(productRepository.findByNameContainingIgnoreCase(anyString(), any(PageRequest.class))).thenReturn(page);

        Page<Product> result = productService.getProductByKeyword("Test", 0);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(product.getId(), result.getContent().get(0).getId());
    }

    @Test
    void testUpdate() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.update(1L, productDTO);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getPrice(), result.getPrice());
        assertEquals(productDTO.getDescription(), result.getDescription());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetTopSellingProducts() {
        Object[] row = {1L, "Test Product", 10, 1000.0};
        var rows  = new ArrayList<Object[]>();
        rows.add(row);
        Page<Object[]> page = new PageImpl<>(rows);
        when(productRepository.findTopSellingProducts(anyInt(), any(PageRequest.class))).thenReturn(page);

        Page<TopSellingProductDTO> result = productService.getTopSellingProducts(10, 0);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals("Test Product", result.getContent().get(0).getName());
        assertEquals(10, result.getContent().get(0).getTotal_quantity());
        assertEquals(1000.0, result.getContent().get(0).getTotal_revenue());
    }

    @Test
    void testFindProductByVendor() {
        List<Product> products = Collections.singletonList(product);

        Page<Product> page = new PageImpl<>(products);

        when(productRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        Page<Product> result = productService.findProductByVendor(1L, 0);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(product.getId(), result.getContent().get(0).getId());
    }

    @Test
    void testGetRepository() {
        assertEquals(productRepository, productService.getRepository());
    }

    @Test
    void testGetEntityClass() {
        assertEquals(Product.class, productService.getEntityClass());
    }

    @Test
    void testGetDtoClass() {
        assertEquals(ProductDTO.class, productService.getDtoClass());
    }

    @Test
    void testFindAll() {
        List<Product> products = Collections.singletonList(product);

        Page<Product> page = new PageImpl<>(products);

        when(productRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Product> result = productService.findAll(0);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(product.getId(), result.getContent().get(0).getId());
    }

    @Test
    void testDelete() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = productService.delete(1L);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).delete(any(Product.class));
    }
}
