package com.sda.dto;

import com.sda.model.User;
import lombok.Builder;

import java.util.Objects;

public record UserDTO(String username,
                      String name,
                      String surname,
                      int age,
                      String email) {

    @Builder public UserDTO{}

}

//public class UserDTO {
//
//    private final String username;
//    private final String name;
//    private final String surname;
//    private final int age;
//    private final String email;
//
//
//    public UserDTO(String username, String name, String surname, int age, String email) {
//        this.username = username;
//        this.name = name;
//        this.surname = surname;
//        this.age = age;
//        this.email = email;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserDTO userDTO = (UserDTO) o;
//        return age == userDTO.age && Objects.equals(username, userDTO.username) && Objects.equals(name, userDTO.name) && Objects.equals(surname, userDTO.surname) && Objects.equals(email, userDTO.email);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(username, name, surname, age, email);
//    }
//
//    @Override
//    public String toString() {
//        return "UserDTO{" +
//                "username='" + username + '\'' +
//                ", name='" + name + '\'' +
//                ", surname='" + surname + '\'' +
//                ", age=" + age +
//                ", email='" + email + '\'' +
//                '}';
//    }
//}
