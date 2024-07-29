package com.example.mycart.utils;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.payloads.BaseDTO;
import com.example.mycart.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    public <T extends BaseEntity<Long>,D extends BaseDTO, ID> boolean validateEntity(GenericService<T,D,ID> service, ID id) {
        return service.findById(id) == null;
    }

    public boolean validateUser(Long id) {
        return validateEntity(userService, id);
    }

    public boolean validateProduct(Long id) {
        return validateEntity(productService, id);
    }

    public boolean validateVendor(Long id) {
        return validateEntity(vendorService, id);
    }

    public boolean validateOrder(Long id) {
        return validateEntity(orderService, id);
    }

    public boolean validateCategory(Long id){
        return validateEntity(categoryService,id);
    }
}
