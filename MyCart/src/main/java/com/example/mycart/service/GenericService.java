package com.example.mycart.service;

import java.util.List;

public interface GenericService<T, ID>
{
    T create(T dto);
    T findById(ID id);
    List<T> findAll();
    T update(ID id, T dto);
    T delete(ID id);
}

