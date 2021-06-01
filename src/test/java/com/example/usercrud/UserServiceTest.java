package com.example.usercrud;

import com.example.usercrud.entities.User;
import com.example.usercrud.repositories.UserRepository;
import com.example.usercrud.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    public void findAll(){
        when(repository.findAll()).thenReturn(Stream.of(new User(123,"ABC","abc@email.com"), new User(124, "DEF", "def@email.com")).collect(Collectors.toList()));
        assertEquals(1, service.findAll().spliterator().getExactSizeIfKnown());
    }

    @Test
    public void findById(){
        when(repository.findById(Integer.toUnsignedLong(123))).thenReturn(Optional.of(new User(123,"ABC","abc@email.com")));
        assertNotNull(service.findById(Integer.toUnsignedLong(123)));
    }
}
