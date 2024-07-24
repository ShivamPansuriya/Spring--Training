package com.example.mycart.service;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.payloads.inheritDTO.BaseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GenericService<T extends BaseEntity,D extends BaseDTO, ID>
{
    T create(D dto);
    T findById(ID id);
    Page<T> findAll(int pageNo);
    T update(ID id, D dto);
    T delete(ID id);
}

