package com.karim.springboot.service;

import com.google.inject.internal.util.ImmutableList;
import com.karim.springboot.DAO.FakeDataDao;
import com.karim.springboot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

        assertUserFields(user);
    }



    @Test
    void shouldGetUser() throws Exception {
        UUID annaUid = UUID.randomUUID();
        User annaUser = new User(annaUid,"anna","montana", User.Gender.FEMALE,22,"anna.montana@gmail.com");
        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(annaUser));
        Optional<User> userOptional = userService.getUser(annaUid);
        assertThat(userOptional.isPresent()).isTrue();
        User user = userOptional.get();
        assertUserFields(user);
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UUID annaUid = UUID.randomUUID();
        User annaUser = new User(annaUid,"anna","montana", User.Gender.FEMALE,22,"anna.montana@gmail.com");
        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(annaUser));
        given(fakeDataDao.updateUser(annaUser)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        int resultUpdateService = userService.updateUser(annaUser);
        verify(fakeDataDao).selectUserByUserUid(annaUid);
        verify(fakeDataDao).updateUser(captor.capture());
        User user = captor.getValue();
        assertUserFields(user);
        assertThat(resultUpdateService).isEqualTo(1);
    }

    @Test
    void removeUser() {
    }

    @Test
    void insertUser() {
    }
    private static void assertUserFields(User user) {
        assertThat(user.getFirstName()).isEqualTo("anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getEmail()).isEqualTo("anna.montana@gmail.com");
    }
}