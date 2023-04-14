package com.sda.mapper;

import com.sda.dto.UserDTO;
import com.sda.model.User;

public class UserMapper {

    public UserDTO map(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
    }

    public User map(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setEmail(dto.email());
        user.setAge(dto.age());
        return user;
    }
}
