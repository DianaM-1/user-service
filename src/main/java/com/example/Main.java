package com.example;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.entity.User;
import com.example.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final UserDao userDao = new UserDaoImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            logger.info("Starting User Service Application");

            boolean running = true;
            while (running) {
                printMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1:
                        createUser();
                        break;
                    case 2:
                        getUserById();
                        break;
                    case 3:
                        getAllUsers();
                        break;
                    case 4:
                        updateUser();
                        break;
                    case 5:
                        deleteUser();
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        } finally {
            HibernateUtil.shutdown();
            scanner.close();
            logger.info("Application shutdown completed");
        }
    }

    private static void printMenu() {
        System.out.println("\nUser Service Menu:");
        System.out.println("1. Create User");
        System.out.println("2. Find User by ID");
        System.out.println("3. List All Users");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("6. Exit");
    }

    private static void createUser() {
        System.out.println("\nCreate New User");
        String name = getStringInput("Enter name: ");
        String email = getStringInput("Enter email: ");
        int age = getIntInput("Enter age: ");

        User user = new User(name, email, age);
        userDao.save(user);
        System.out.println("User created successfully: " + user);
        logger.info("Created user: {}", user);
    }

    private static void getUserById() {
        System.out.println("\nFind User by ID");
        Long id = getLongInput("Enter user ID: ");
        User user = userDao.findById(id);

        if (user != null) {
            System.out.println("User found: " + user);
            logger.info("Retrieved user by ID {}: {}", id, user);
        } else {
            System.out.println("User not found with ID: " + id);
            logger.warn("User not found with ID: {}", id);
        }
    }

    private static void getAllUsers() {
        System.out.println("\nList All Users");
        List<User> users = userDao.findAll();

        if (users != null && !users.isEmpty()) {
            System.out.println("Users:");
            users.forEach(System.out::println);
            logger.info("Retrieved all users, count: {}", users.size());
        } else {
            System.out.println("No users found.");
            logger.info("No users found in database");
        }
    }

    private static void updateUser() {
        System.out.println("\nUpdate User");
        Long id = getLongInput("Enter user ID to update: ");
        User user = userDao.findById(id);

        if (user != null) {
            System.out.println("Current user details: " + user);
            String name = getStringInput("Enter new name (leave blank to keep current): ");
            String email = getStringInput("Enter new email (leave blank to keep current): ");
            String ageInput = getStringInput("Enter new age (leave blank to keep current): ");

            if (!name.isEmpty()) user.setName(name);
            if (!email.isEmpty()) user.setEmail(email);
            if (!ageInput.isEmpty()) user.setAge(Integer.parseInt(ageInput));

            userDao.update(user);
            System.out.println("User updated successfully: " + user);
            logger.info("Updated user: {}", user);
        } else {
            System.out.println("User not found with ID: " + id);
            logger.warn("Update failed - user not found with ID: {}", id);
        }
    }

    private static void deleteUser() {
        System.out.println("\nDelete User");
        Long id = getLongInput("Enter user ID to delete: ");
        User user = userDao.findById(id);

        if (user != null) {
            userDao.delete(user);
            System.out.println("User deleted successfully with ID: " + id);
            logger.info("Deleted user with ID: {}", id);
        } else {
            System.out.println("User not found with ID: " + id);
            logger.warn("Delete failed - user not found with ID: {}", id);
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static Long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}