package com.example.usercrud.service;

import com.example.usercrud.entities.User;
import com.example.usercrud.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockUserRepository);
    }

    @Test
    void testFindAll() {
        // Setup

        // Configure UserRepository.findAll(...).
        final Iterable<User> users = Arrays.asList(new User(0L, "name", "email"));
        when(mockUserRepository.findAll()).thenReturn(users);

        // Run the test
        final Iterable<User> result = userServiceUnderTest.findAll();

        // Verify the results
    }

    @Test
    void testFindAll_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final Iterable<User> result = userServiceUnderTest.findAll();

        // Verify the results
    }

    @Test
    void testSave() {
        // Setup
        final User user = new User(0L, "name", "email");
        when(mockUserRepository.save(new User(0L, "name", "email"))).thenReturn(new User(0L, "name", "email"));

        // Run the test
        userServiceUnderTest.save(user);

        // Verify the results
    }

    @Test
    void testFindById() {
        // Setup
        final User expectedResult = new User(0L, "name", "email");

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User(0L, "name", "email"));
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.findById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testDelete() {
        // Setup
        final User user = new User(0L, "name", "email");

        // Run the test
        userServiceUnderTest.delete(user);

        // Verify the results
        verify(mockUserRepository).delete(new User(0L, "name", "email"));
    }
}
