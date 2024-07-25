package com.example.mycart.service;

import com.example.mycart.model.User;
import com.example.mycart.payloads.UserDTO;

public interface UserService extends GenericService<User,UserDTO,Long>
{
    User getUserByName(String name);

    User findUserById(Long id);
}

