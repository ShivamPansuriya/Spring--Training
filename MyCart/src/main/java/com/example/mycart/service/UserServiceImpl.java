package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.User;
import com.example.mycart.payloads.UserDTO;
import com.example.mycart.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractGenericService<User, UserDTO, Long>implements UserService
{
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

//    @Override
//    public List<UserDTO> getAllUsers()
//    {
//        var users = repository.findAll();
//
//        return users.stream().map(user -> mapper.map(user,UserDTO.class)).toList();
//    }
//
//    @Override
//    @Cacheable
//    public UserDTO getUserById(Long id)
//    {
//        var user = findUserById(id);
//
//        return mapper.map(user, UserDTO.class);
//    }
//
//
//    @Override
//    @Transactional
//    public UserDTO createUser(UserDTO userDTO)
//    {
//        var user = mapper.map(userDTO, User.class);
//
//        var savedUser = repository.save(user);
//
//        return mapper.map(savedUser, UserDTO.class);
//    }
//
//    @Override
//    @Transactional
//    @CachePut
//    public UserDTO updateUser(Long id, UserDTO userDTO)
//    {
//        var user = repository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException("User","id",id));
//
//        user.setEmail(userDTO.getEmail());
//
//        user.setName(userDTO.getName());
//
//        user.setAddress(userDTO.getAddress());
//
//        var updatedUser = repository.save(user);
//
//        return mapper.map(updatedUser, UserDTO.class);
//    }
//
//    @Override
//    @Transactional
//    @CacheEvict
//    public UserDTO deleteUser(Long id)
//    {
//        var user = repository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException("User","id",id));
//
//        repository.delete(user);
//
//        return mapper.map(user, UserDTO.class);
//    }
    @Override
    public User findUserById(Long id)
    {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",id));
    }

    @Override
    public UserDTO getUserByName(String name)
    {
        var user = repository.findByName(name)
                .orElseThrow(()-> new ResourceNotFoundException("User","name",name));

        return mapper.map(user, UserDTO.class);
    }

    @Override
    protected JpaRepository<User, Long> getRepository() {
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
