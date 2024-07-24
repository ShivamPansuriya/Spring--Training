package com.example.mycart.service;

import com.example.mycart.model.User;
import com.example.mycart.payloads.inheritDTO.UserDTO;

public interface UserService extends GenericService<User,UserDTO,Long>
{
//    List<UserDTO> getAllUsers();
//
//    UserDTO getUserById(Long id);
//
//    UserDTO createUser(UserDTO user);
//
//    UserDTO updateUser(Long id, UserDTO userDTO);
//
//    UserDTO deleteUser(Long id);

    User getUserByName(String name);

    User findUserById(Long id);
}

