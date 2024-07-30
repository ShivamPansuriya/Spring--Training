package com.example.mycart.modelmapper;

import com.example.mycart.model.Product;
import com.example.mycart.model.Review;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.ReviewDTO;
import com.example.mycart.service.CategoryService;
import com.example.mycart.service.ReviewService;
import com.example.mycart.service.VendorService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl extends ProductMapper<Product,ProductDTO>
{
    private final CategoryService categoryService;

    private final VendorService vendorService;

    private final ReviewService reviewService;

    private final ReviewMapper<Review, ReviewDTO> reviewMapper;

    public ProductMapperImpl(CategoryService categoryService, VendorService vendorService, ReviewService reviewService, ReviewMapper<Review, ReviewDTO> reviewMapper) {
        this.categoryService = categoryService;
        this.vendorService = vendorService;
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @Override
    protected CategoryService getCategoryService() {
        return categoryService;
    }

    @Override
    protected VendorService getVendorService() {
        return vendorService;
    }

    @Override
    public ProductDTO toDTO(Product entity, int pageNo) {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new ProductDTO());
        dto.setCategoryName(getCategoryName(entity.getCategoryId()));
        dto.setVendorName(getVendorName(entity.getVendorId()));
        dto.setPrice(entity.getPrice());
        dto.setReviews(reviewMapper.toDTOs(reviewService.getReviewsByProductId(entity.getId(),pageNo),0));
        return dto;
    }

    @Override
    public Product toEntity(ProductDTO dto) {
        if(dto == null)
        {
            return null;
        }
        var entity = mapToBaseEntity(dto,new Product());
        entity.setPrice(dto.getPrice());
        return entity;
    }
}
