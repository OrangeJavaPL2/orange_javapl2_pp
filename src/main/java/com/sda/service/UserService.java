package com.sda.service;

import com.sda.dao.UsersDAO;
import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.exception.UsernameConflictException;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UsersDAO usersDAO;
    private final UserMapper userMapper;

    public List<UserDTO> findAll() {
//        List<User> users = usersDAO.findAll();
//        List<UserDTO> userDTOS = users.stream()
//                .map(user -> userMapper.map(user))
//                .toList();
//        return userDTOS;

        return usersDAO.findAll().stream()
                .map(userMapper::map)
                .toList();
    }

    public UserDTO findByUsername(String username) {
        User user = usersDAO.findByUsername(username);
        if (user == null) {
            throw new NotFoundException(
                    "User with username '%s' does not exists!"
                            .formatted(username));
        }
        return userMapper.map(user);
    }

    public void deleteByUsername(String username) {
        boolean deleted = usersDAO.deleteByUsername(username);
        if (!deleted) {
            throw new NotFoundException(
                    "User with username '%s' does not exists!".formatted(username)
            );
        }
    }

    public void create(User user) {
        String username = user.getUsername();
        boolean exists = usersDAO.exists(username);

        if (exists) {
            throw new UsernameConflictException(
                    "User with username '%s' already exists!".formatted(username)
            );
        }
        usersDAO.create(user);
    }

    public UserDTO update(User user, String username) {
        String updateUsername = user.getUsername();

        if (!updateUsername.equals(username)) {
            throw new UsernameConflictException("Usernames conflict!");
        }

        boolean exists = usersDAO.exists(username);

        if (!exists) {
            throw new NotFoundException(
                    "User with username '%s' does not exists!".formatted(username)
            );
        }
        User update = usersDAO.update(user);
        return userMapper.map(update);
    }
}
