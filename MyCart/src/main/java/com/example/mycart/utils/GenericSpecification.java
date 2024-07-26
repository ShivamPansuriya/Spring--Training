package com.example.mycart.utils;

import org.springframework.data.jpa.domain.Specification;


public class GenericSpecification {

    public static <T> Specification<T> getList(String attributeName, Long id)
    {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(attributeName), id);
    }
}

