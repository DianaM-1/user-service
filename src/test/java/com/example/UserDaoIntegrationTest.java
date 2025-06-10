package com.example;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.entity.User;
import com.example.testutils.TestSession;
import com.example.util.HibernateUtil;
import org.junit.jupiter.api.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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
    private static SessionFactory sessionFactory;

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
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void save_ShouldPersistUser_WhenValidUser() {
        User user = new User("John Doe", "john@example.com", 30);

        userDao.save(user);
        assertNotNull(user.getId());

        User retrievedUser = userDao.findById(user.getId());
        assertEquals(user.getName(), retrievedUser.getName());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getAge(), retrievedUser.getAge());
        assertNotNull(retrievedUser.getCreatedAt());
    }

    @Test
    void testSaveUserWithNullName_ShouldThrowException() {
        User user = new User(null, "nullname@example.com", 25);

        Assertions.assertThrows(Exception.class, () -> {
            userDao.save(user);
        });
    }

    @Test
    void testSaveUserWithNullEmail_ShouldThrowException() {
        User invalidUser = new User("John Doe", null, 30);
        assertThrows(RuntimeException.class, () -> userDao.save(invalidUser));

        assertNull(invalidUser.getId());
        assertTrue(userDao.findAll().isEmpty());
    }

    @Test
    void testSaveUserWithNullAge_ShouldThrowException() {
        User user = new User("Null Age", "nullage@example.com", null);

        Assertions.assertThrows(Exception.class, () -> {
            userDao.save(user);
        });
    }

    @Test
    void testSaveUserWithNegativeAge_ShouldThrowException() {
        User user = new User("Negative Age", "negative@example.com", -5);

        Assertions.assertThrows(Exception.class, () -> {
            userDao.save(user);
        });
    }

    @Test
    void save_ShouldThrowException_WhenTransactionIsNull() {
        User user = new User("John Doe", "john@example.com", 30);

        try (Session realSession = sessionFactory.openSession()) {
            TestSession testSession = new TestSession(realSession);
            testSession.setReturnNullTransaction(true);

            UserDaoImpl brokenDao = new UserDaoImpl() {
                @Override
                protected Session getSession() {
                    return testSession;
                }
            };

            assertThrows(RuntimeException.class, () -> brokenDao.save(user));
        }
    }

    @Test
    void findById_ShouldThrowIllegalArgumentException_WhenIdIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userDao.findById(null),
                "Expected IllegalArgumentException for null ID"
        );
    }

    @Test
    void findById_ShouldCloseSession_EvenIfExceptionOccurs() {
        assertThrows(IllegalArgumentException.class, () -> userDao.findById(null));

        User user = new User("Test", "test@example.com", 30);
        userDao.save(user);
        assertNotNull(userDao.findById(user.getId()));
    }

    @Test
    void findById_ShouldReturnNull_WhenUserDoesNotExist() {
        User foundUser = userDao.findById(999L);

        assertNull(foundUser);
    }

    @Test
    void findAll_ShouldReturnAllUsers_WhenUsersExist() {
        userDao.save(new User("John Doe", "john@example.com", 30));
        userDao.save(new User("Jane Smith", "jane@example.com", 25));

        List<User> users = userDao.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoUsersExist() {
        List<User> users = userDao.findAll();

        assertTrue(users.isEmpty());
    }


    @Test
    void update_ShouldUpdateUser_WhenValidUser() {
        User user = new User("John Doe", "john@example.com", 30);
        userDao.save(user);

        user.setName("John Updated");
        user.setEmail("updated@example.com");
        user.setAge(31);
        userDao.update(user);

        User updatedUser = userDao.findById(user.getId());
        assertEquals("John Updated", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
        assertEquals(31, updatedUser.getAge());
    }

    @Test
    void update_ShouldThrowException_WhenTransactionFails() {
        User user = new User("John Doe", "john@example.com", 30);

        assertThrows(RuntimeException.class, () -> userDao.update(user));
    }

    @Test
    void update_ShouldThrowException_WhenUserDoesNotExist() {
        User nonExistentUser = new User("Ghost", "ghost@example.com", 100);
        nonExistentUser.setId(999L);

        assertThrows(RuntimeException.class, () -> userDao.update(nonExistentUser));
    }

    @Test
    void delete_ShouldRemoveUser_WhenUserExists() {
        User user = new User("John Doe", "john@example.com", 30);

        userDao.save(user);
        userDao.delete(user);

        assertNull(userDao.findById(user.getId()));
    }

    @Test
    void delete_ShouldThrowException_WhenUserDoesNotExist() {
        User nonExistentUser = new User("Ghost", "ghost@example.com", 100);
        nonExistentUser.setId(999L);

        assertThrows(RuntimeException.class, () -> userDao.delete(nonExistentUser));
    }
}