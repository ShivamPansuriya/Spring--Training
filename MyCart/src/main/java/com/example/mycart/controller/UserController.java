package com.example.mycart.controller;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.User;
import com.example.mycart.modelmapper.EntityMapper;
import com.example.mycart.modelmapper.UserMapper;
import com.example.mycart.payloads.UserDTO;
import com.example.mycart.service.UserService;
import com.example.mycart.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractGenericController<User,UserDTO,Long>
{
    @Autowired
    private UserService service;

    @Autowired
    private UserMapper<User,UserDTO> mapper;

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String name)
    {
        var user = service.getUserByName(name);

        if(user==null)
            throw new ResourceNotFoundException("User","name",name);

        return new ResponseEntity<>(mapper.toDTO(user,0), HttpStatus.OK);
    }

    @Override
    protected EntityMapper<User, UserDTO> getMapper() {
        return mapper;
    }

    @Override
    protected GenericService<User,UserDTO, Long> getService() {
        return service;
    }
}
