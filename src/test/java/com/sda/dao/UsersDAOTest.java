package com.sda.dao;

import com.github.javafaker.Faker;
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
        final User expectedUser = getUser(username);

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
        final User expectedUser = getUser(username);

        // when
        Executable executable = () -> usersDAO.create(expectedUser);

        // then
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    @Test
    void testCreateConstrainsViolationNullSurname() {
        // given
        final String username = UUID.randomUUID().toString();
        final User expectedUser = getUser(username);

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
        final User expectedUser = getUser(username);

        usersDAO.create(expectedUser);

        // when
        boolean exists = usersDAO.exists(username);

        // then
        Assertions.assertTrue(exists);
    }

    @Test
    void testFindByUsernameNotFound() {
        // given
        final String nonExistingUsername = "nonExistingUsername";

        // when
        User user = usersDAO.findByUsername(nonExistingUsername);

        // then
        Assertions.assertNull(user);
    }

    @Test
    void testFindByUsernameHappyPath() {
        // given
        final String username = UUID.randomUUID().toString();
        final User expectedUser = getUser(username);
        usersDAO.create(expectedUser);

        // when
        User actualUser = usersDAO.findByUsername(username);

        // then
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @Test
    void testDeleteByUsernameNonExistingUser() {
        // given
        final String nonExistingUsername = "nonExistingUsername";

        // when
        boolean deleted = usersDAO.deleteByUsername(nonExistingUsername);

        // then
        Assertions.assertFalse(deleted);
    }

    @Test
    void testDeleteByUsernameSuccess() {
        // given
        final String username = UUID.randomUUID().toString();
        final User user = getUser(username);
        usersDAO.create(user);

        // when
        boolean deleted = usersDAO.deleteByUsername(username);

        // then
        Assertions.assertTrue(deleted);
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