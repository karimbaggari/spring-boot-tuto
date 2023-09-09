package com.karim.springboot.service;

import com.google.inject.internal.util.ImmutableList;
import com.karim.springboot.DAO.FakeDataDao;
import com.karim.springboot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

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
        UUID rosaUserUid = UUID.randomUUID();
        User rosaUser = new User(rosaUserUid,"rosa","montana", User.Gender.FEMALE,22,"rosa.montana@gmail.com");
        ImmutableList<User> users = new ImmutableList.Builder<User>().add(rosaUser).build();
        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers = userService.getAllUsers();
        assertThat(allUsers).hasSize(1);
        User user = allUsers.get(0);

        assertThat(user.getFirstName()).isEqualTo("rosa");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getEmail()).isEqualTo("rosa.montana@gmail.com");
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