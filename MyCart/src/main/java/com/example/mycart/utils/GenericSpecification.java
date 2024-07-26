package com.example.mycart.utils;

import com.example.mycart.model.Category;
import com.example.mycart.model.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.awt.print.Pageable;

public class GenericSpecification {

    public static <T> Specification<T> getList(String attributeName, Long id)
    {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(attributeName), id);
    }
}

