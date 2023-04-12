package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.UUID;

class UsersDAOTest {

    private final UsersDAO usersDAO = new UsersDAO();

    @Test
    void testCreateHappyPath() {
        // given
        final String username = UUID.randomUUID().toString();
        final User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setAge(27);
        expectedUser.setName("Sue");
        expectedUser.setSurname("Doe");
        expectedUser.setPassword("Pass");
        expectedUser.setEmail("username@example.com");

        // when
        usersDAO.create(expectedUser);

        // then
        try (Session session = HibernateUtils.openSession()) {
            User actualUser = session.find(User.class, username);

            Assertions.assertEquals(expectedUser, actualUser);
            Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
            Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
            Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
            Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
            Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
            Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        }
    }

    @Test
    void testCreateConstrainsViolationNullName() {
        // given
        final String username = UUID.randomUUID().toString();
        final User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setAge(27);
        expectedUser.setSurname("Doe");
        expectedUser.setPassword("Pass");
        expectedUser.setEmail("username@example.com");

        // when
        Executable executable = () -> usersDAO.create(expectedUser);

        // then
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    @Test
    void testCreateConstrainsViolationNullSurname() {
        // given
        final String username = UUID.randomUUID().toString();
        final User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setName("Sue");
        expectedUser.setAge(27);
        expectedUser.setPassword("Pass");
        expectedUser.setEmail("username@example.com");

        // when
        Executable executable = () -> usersDAO.create(expectedUser);

        // then
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    @Test
    void testExistsShouldReturnFalse() {
        // given
        final String nonExistingUsername = "nonExistingUsername";

        // when
        boolean exists = usersDAO.exists(nonExistingUsername);

        // then
        Assertions.assertFalse(exists);
    }

    @Test
    void testExistsShouldReturnTrue() {
        // given
        final String username = UUID.randomUUID().toString();
        final User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setAge(27);
        expectedUser.setName("Sue");
        expectedUser.setSurname("Doe");
        expectedUser.setPassword("Pass");
        expectedUser.setEmail("username@example.com");

        usersDAO.create(expectedUser);

        // when
        boolean exists = usersDAO.exists(username);

        // then
        Assertions.assertTrue(exists);
    }
}