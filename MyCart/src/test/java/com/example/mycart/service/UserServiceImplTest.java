package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.User;
import com.example.mycart.payloads.UserDTO;
import com.example.mycart.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("TestUser");
        user.setEmail("test@example.com");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("TestUser");
        userDTO.setEmail("test@example.com");
    }

    @Test
    void testCreate() {
        when(repository.save(any(User.class))).thenReturn(user);

        User result = userService.create(user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void testFindById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(1L));
        verify(repository, times(1)).findById(anyLong());
    }

//    @Test
//    void testFindAll() {
//        List<User> users = Collections.singletonList(user);
//        Page<User> page = new PageImpl<>(users);
//        when(repository.findAll(any(PageRequest.class))).thenReturn(page);
//
//        Page<User> result = userService.findAll(0);
//
//        assertNotNull(result);
//        assertEquals(1, result.getTotalElements());
//        assertEquals(user.getId(), result.getContent().get(0).getId());
//        verify(repository, times(1)).findAll(any(PageRequest.class));
//    }

    @Test
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenReturn(user);

        User result = userService.update(1L, userDTO);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.delete(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(User.class));
    }

    @Test
    void testGetUserByName() {
        when(repository.findByName(anyString())).thenReturn(Optional.of(user));

        User result = userService.getUserByName("TestUser");

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(repository, times(1)).findByName(anyString());
    }

    @Test
    void testGetUserByName_NotFound() {
        when(repository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByName("NonexistentUser"));
        verify(repository, times(1)).findByName(anyString());
    }

    @Test
    void testGetRepository() {
        assertEquals(repository, userService.getRepository());
    }

    @Test
    void testGetEntityClass() {
        assertEquals(User.class, userService.getEntityClass());
    }

    @Test
    void testGetDtoClass() {
        assertEquals(UserDTO.class, userService.getDtoClass());
    }
}