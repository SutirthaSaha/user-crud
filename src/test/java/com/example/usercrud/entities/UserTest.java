package com.example.usercrud.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User userUnderTest;

    @BeforeEach
    void setUp() {
        userUnderTest = new User(0L, "name", "email");
    }

    @Test
    void testToString() {
        // Setup

        // Run the test
        final String result = userUnderTest.toString();

        // Verify the results
        assertEquals("User{id=0, name=name, email=email}", result);
    }

    @Test
    void testEquals() {
        // Setup

        // Run the test
        final boolean result = userUnderTest.equals("o");

        // Verify the results
        assertTrue(!result);
    }

    @Test
    void testHashCode() {
        // Setup

        // Run the test
        final int result = userUnderTest.hashCode();

        // Verify the results
        assertNotEquals(0, result);
    }
}
