package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.User;
import com.example.mycart.payloads.UserDTO;
import com.example.mycart.repository.BaseRepository;
import com.example.mycart.repository.SoftDeletesRepository;
import com.example.mycart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractGenericService<User, UserDTO, Long>implements UserService
{
    @Autowired
    private UserRepository repository;

    @Override
    public User getUserByName(String name)
    {
        return repository.findByName(name)
                .orElse(null);
    }

    @Override
    protected SoftDeletesRepository<User, Long> getRepository() {
        return repository;
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<UserDTO> getDtoClass() {
        return UserDTO.class;
    }
}
