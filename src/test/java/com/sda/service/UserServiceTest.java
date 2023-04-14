package com.sda.service;

import com.github.javafaker.Faker;
import com.sda.dao.UsersDAO;
import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

//    private final UsersDAO usersDAO = Mockito.mock(UsersDAO.class);
//    private final UserMapper userMapper = new UserMapper();
//    private final UserService userService = new UserService(usersDAO, userMapper);

    @Mock
    private UsersDAO usersDAO;

    @Spy
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        Mockito.reset(usersDAO);
    }

    @Test
    void testFindByUsernameSuccess() {
        // given
        final String username = UUID.randomUUID().toString();
        final User user = getUser(username);
        final UserDTO expectedUser = userMapper.map(user);

        Mockito.when(usersDAO.findByUsername(username)).thenReturn(user);

        // when
        UserDTO actualUser = userService.findByUsername(username);

        // then
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.username(), actualUser.username());
        Assertions.assertEquals(expectedUser.name(), actualUser.name());
        Assertions.assertEquals(expectedUser.surname(), actualUser.surname());
        Assertions.assertEquals(expectedUser.age(), actualUser.age());
        Assertions.assertEquals(expectedUser.email(), actualUser.email());

        Mockito.verify(usersDAO).findByUsername(username);
        Mockito.verifyNoMoreInteractions(usersDAO);
    }

    @Test
    void testFindByUsernameUserNotExists() {
        // given
        final String nonExistingUsername = "nonExistingUsername";

        // when
        Executable executable = () -> userService.findByUsername(nonExistingUsername);

        // then
        Assertions.assertThrows(NotFoundException.class, executable);
        Mockito.verify(usersDAO).findByUsername(nonExistingUsername);
        Mockito.verifyNoMoreInteractions(usersDAO);
    }

    private User getUser(String username) {
        Faker faker = new Faker();

        final User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setAge(faker.number().numberBetween(1, 120));
        expectedUser.setName(faker.name().firstName());
        expectedUser.setSurname(faker.name().lastName());
        expectedUser.setPassword(faker.internet().password());
        expectedUser.setEmail(faker.internet().emailAddress());
        return expectedUser;
    }
}