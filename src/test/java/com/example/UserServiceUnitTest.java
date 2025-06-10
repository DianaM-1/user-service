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
        assertEquals("test@example.com", created.getEmail());
        assertEquals(30, created.getAge());
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
    void getUserById_WithNonExistentId_ShouldReturnNull() {
        when(userDao.findById(999L)).thenReturn(null);

        User found = userService.getUserById(999L);

        assertNull(found);
        verify(userDao, times(1)).findById(999L);
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
    void updateUser_WithNonExistentId_ShouldDoNothing() {
        when(userDao.findById(999L)).thenReturn(null);

        userService.updateUser(999L, "Updated Name", "updated@example.com", 35);

        verify(userDao, never()).update(any(User.class));
    }

    @Test
    void deleteUser_ShouldDeleteExistingUser() {
        when(userDao.findById(1L)).thenReturn(testUser);

        userService.deleteUser(1L);

        verify(userDao, times(1)).delete(any(User.class));
    }

    @Test
    void deleteUser_WithNonExistentId_ShouldDoNothing() {
        when(userDao.findById(999L)).thenReturn(null);

        userService.deleteUser(999L);

        verify(userDao, never()).delete(any(User.class));
    }

    @Test
    void updateUser_UpdateOnlyName_ShouldSucceed() {
        when(userDao.findById(1L)).thenReturn(testUser);

        userService.updateUser(1L, "New Name", null, null);

        assertEquals("New Name", testUser.getName());
        assertEquals("test@example.com", testUser.getEmail());
        assertEquals(30, testUser.getAge());
    }

    @Test
    void updateUser_UpdateOnlyEmail_ShouldSucceed() {
        when(userDao.findById(1L)).thenReturn(testUser);

        userService.updateUser(1L, null, "new@example.com", null);

        assertEquals("Test User", testUser.getName());
        assertEquals("new@example.com", testUser.getEmail());
        assertEquals(30, testUser.getAge());
    }

    @Test
    void updateUser_UpdateOnlyAge_ShouldSucceed() {
        when(userDao.findById(1L)).thenReturn(testUser);

        userService.updateUser(1L, null, null, 35);

        assertEquals("Test User", testUser.getName());
        assertEquals("test@example.com", testUser.getEmail());
        assertEquals(35, testUser.getAge());
    }
}