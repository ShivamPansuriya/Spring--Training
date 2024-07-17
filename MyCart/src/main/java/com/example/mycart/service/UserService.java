package com.example.mycart.service;

import com.example.mycart.model.User;
import com.example.mycart.payloads.UserDTO;

import java.util.List;

public interface UserService
{
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO deleteUser(Long id);
    UserDTO getUserByName(String name);
    User findUserById(Long id);
}
