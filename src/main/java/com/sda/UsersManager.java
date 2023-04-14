package com.sda;

import com.sda.controller.InputController;
import com.sda.controller.UserController;
import com.sda.dao.UsersDAO;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import com.sda.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class UsersManager {

    private final static UserController userController = new UserController(
            new UserService(new UsersDAO(), new UserMapper())
    );

    private final static InputController inputController = new InputController(
            new Scanner(System.in)
    );

    public static void main(String[] args) {

        final String options = """
                Options:
                1 - List users
                2 - Find user
                3 - Create user
                4 - Delete user
                5 - Update users
                6 - Exit
                """;
        String option;
        do {
            option = inputController.getString(options);

            switch (option) {
                case "1" -> userController.findAll();
                case "2" -> {
                    String username = inputController.getUsername();
                    userController.findByUsername(username);
                }
                case "3" -> {
                    User user = getUserData();
                    userController.create(user);
                }
                case "4" -> {
                    String username = inputController.getUsername();
                    userController.deleteByUsername(username);
                }
                case "5" -> {
                    User user = getUserData();
                    String username = inputController.getUsername();
                    userController.update(user, username);
                }
                case "6" -> System.out.println("Bye!");
                default -> System.err.println("Invalid option!");
            }

        } while (!"6".equals(option));
    }

    private static User getUserData() {
        String username = inputController.getUsername();
        String name = inputController.getName();
        String surname = inputController.getSurname();
        int age = inputController.getAge();
        String email = inputController.getEmail();
        String password = inputController.getPassword();

        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
