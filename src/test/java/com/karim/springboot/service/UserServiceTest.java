package com.karim.springboot.service;

import com.karim.springboot.DAO.FakeDataDao;
import com.karim.springboot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @Mock

    private FakeDataDao fakeDataDao;

    private UserService userService;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        List<User> allUsers = userService.getAllUsers();
        assertThat(allUsers).hasSize(1);
    }

    @Test
    void getUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void removeUser() {
    }

    @Test
    void insertUser() {
    }
}