package com.example;

import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("Test User", "test@example.com", 30);
        testUser.setId(1L);
        testUser.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createUser_ShouldReturnSavedUser() {
        doNothing().when(userDao).save(any(User.class));

        User created = userService.createUser("Test User", "test@example.com", 30);

        assertNotNull(created);
        assertEquals("Test User", created.getName());
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userDao.findById(1L)).thenReturn(testUser);

        User found = userService.getUserById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        verify(userDao, times(1)).findById(1L);
    }

    @Test
    void getAllUsers_ShouldReturnUserList() {
        when(userDao.findAll()).thenReturn(Arrays.asList(testUser));

        List<User> users = userService.getAllUsers();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        verify(userDao, times(1)).findAll();
    }

    @Test
    void updateUser_ShouldUpdateExistingUser() {
        when(userDao.findById(1L)).thenReturn(testUser);

        userService.updateUser(1L, "Updated Name", "updated@example.com", 35);

        verify(userDao, times(1)).update(any(User.class));
    }

    @Test
    void deleteUser_ShouldDeleteExistingUser() {
        when(userDao.findById(1L)).thenReturn(testUser);

        userService.deleteUser(1L);

        verify(userDao, times(1)).delete(any(User.class));
    }
}