package com.sda.controller;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class InputController {

    private final Scanner scanner;

    public String getUsername() {
        return getString("Username: ");
    }

    public String getName() {
        return getString("Name: ");
    }

    public String getSurname() {
        return getString("Surname: ");
    }

    public int getAge() {
        while (true) {
            try {
                String age = getString("Age: ");
                return Integer.parseInt(age);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input!");
            }
        }
    }

    public String getEmail() {
        return getString("Email: ");
    }

    public String getPassword() {
        return getString("Password: ");
    }

    public String getString(String text) {
        System.out.println(text);
        return scanner.nextLine();
    }
}
