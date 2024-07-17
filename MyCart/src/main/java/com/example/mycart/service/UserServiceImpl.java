package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.User;
import com.example.mycart.payloads.UserDTO;
import com.example.mycart.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UserDTO> getAllUsers() {
        var users = repository.findAll();

        return users.stream().map(user -> mapper.map(user,UserDTO.class)).toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        var user = findUserById(id);

        return mapper.map(user, UserDTO.class);
    }

    @Override
    public User findUserById(Long id)
    {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",id));
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        var user = mapper.map(userDTO, User.class);
        var savedUser = repository.save(user);
        return mapper.map(savedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        var user = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",id));

        user.setEmail(userDTO.getEmail());

        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());

        var updatedUser = repository.save(user);

        return mapper.map(updatedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO deleteUser(Long id) {
        var user = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",id));

        repository.delete(user);
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByName(String name) {
        var user = repository.findByName(name)
                .orElseThrow(()-> new ResourceNotFoundException("User","name",name));

        return mapper.map(user, UserDTO.class);
    }
}
