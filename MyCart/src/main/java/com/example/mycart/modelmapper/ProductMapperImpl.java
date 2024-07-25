package com.example.mycart.modelmapper;

import com.example.mycart.model.Product;
import com.example.mycart.model.Review;
import com.example.mycart.payloads.ProductDTO;
import com.example.mycart.payloads.ReviewDTO;
import com.example.mycart.service.CategoryService;
import com.example.mycart.service.ReviewService;
import com.example.mycart.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl extends ProductMapper<Product,ProductDTO>
{
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMapper<Review, ReviewDTO> reviewMapper;

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
