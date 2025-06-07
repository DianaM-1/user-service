package com.example;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.entity.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;


@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>("postgres:14.1-alpine")
                    .withDatabaseName("test_db")
                    .withUsername("test")
                    .withPassword("test");

    private static UserDao userDao;

    @BeforeAll
    static void setup() {
        System.out.println("JDBC URL: " + postgresqlContainer.getJdbcUrl());

        try (Connection conn = DriverManager.getConnection(
                postgresqlContainer.getJdbcUrl(),
                postgresqlContainer.getUsername(),
                postgresqlContainer.getPassword()
        )) {
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        userDao = new UserDaoImpl();
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    @Order(1)
    void testSaveUser() {
        User user = new User("Test User", "test@example.com", 30);
        userDao.save(user);

        Assertions.assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    void testFindById() {
        User user = userDao.findById(1L);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Test User", user.getName());
    }

    @Test
    @Order(3)
    void testFindAll() {
        List<User> users = userDao.findAll();

        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals(1, users.size());
    }

    @Test
    @Order(4)
    void testUpdateUser() {
        User user = userDao.findById(1L);
        user.setName("Updated Name");
        userDao.update(user);

        User updatedUser = userDao.findById(1L);
        Assertions.assertEquals("Updated Name", updatedUser.getName());
    }

    @Test
    @Order(5)
    void testDeleteUser() {
        User user = userDao.findById(1L);
        userDao.delete(user);

        User deletedUser = userDao.findById(1L);
        Assertions.assertNull(deletedUser);
    }
}