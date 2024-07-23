package com.example.mycart.controller;

import com.example.mycart.payloads.UserDTO;
import com.example.mycart.service.UserService;
import com.example.mycart.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractGenericController<UserDTO,Long>
{
    @Autowired
    private UserService service;

//    @GetMapping
//    public ResponseEntity<List<UserDTO>> getAllUsers()
//    {
//        var users = service.findAll();
//
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id)
//    {
//        var user = service.findById(id);
//
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO)
//    {
//        var createdUser = service.create(userDTO);
//
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO)
//    {
//        userDTO.setId(id);
//        var updatedUser = service.update(id, userDTO);
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id)
//    {
//        var deleteUser = service.delete(id);
//        return new ResponseEntity<>(deleteUser,HttpStatus.OK);
//    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String name)
    {
        var user = service.getUserByName(name);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    protected GenericService<UserDTO, Long> getService() {
        return service;
    }
}
