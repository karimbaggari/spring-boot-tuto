package com.karim.springboot.Service;

import com.google.inject.internal.util.ImmutableList;
import com.karim.springboot.DAO.FakeDataDao;
import com.karim.springboot.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
        User rosaUser = new User(rosaUserUid,"anna","montana", User.Gender.FEMALE,22,"anna.montana@gmail.com");
        ImmutableList<User> users = new ImmutableList.Builder<User>().add(rosaUser).build();
        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers = userService.getAllUsers(Optional.of("female"));
        assertThat(allUsers).hasSize(1);
        User user = allUsers.get(0);

        assertAnnaFields(user);
    }

    @Test
    void shouldgetAllUsersByGender() throws Exception {
        UUID annaUserUid = UUID.randomUUID();
        User annaUser = new User(annaUserUid,"anna","montana", User.Gender.FEMALE,22,"anna.montana@gmail.com");
        UUID jonesUserUid = UUID.randomUUID();
        User jonesUser = new User(jonesUserUid,"jones","montana", User.Gender.MALE,45,"jones.montana@gmail.com");
        ImmutableList<User> users = new ImmutableList.Builder()
                .add(annaUser)
                .add(jonesUser)
                .build();
        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> filteredUsers = userService.getAllUsers(Optional.of("FEMALE"));
        assertThat(filteredUsers).hasSize(1);
        assertAnnaFields(filteredUsers.get(0));
    }



    @Test
    void shouldGetUser() throws Exception {
        UUID annaUid = UUID.randomUUID();
        User annaUser = new User(annaUid,"anna","montana", User.Gender.FEMALE,22,"anna.montana@gmail.com");
        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(annaUser));
        Optional<User> userOptional = userService.getUser(annaUid);
        assertThat(userOptional.isPresent()).isTrue();
        User user = userOptional.get();
        assertAnnaFields(user);
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
        assertAnnaFields(user);
        assertThat(resultUpdateService).isEqualTo(1);
    }

    @Test
    void shouldRemoveUser() {
        UUID annaUid = UUID.randomUUID();
        User annaUser = new User(annaUid,"anna","montana", User.Gender.FEMALE,22,"anna.montana@gmail.com");
        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(annaUser));
        given(fakeDataDao.deleteUserByUserUid(annaUid)).willReturn(1);

        int deleteResult = userService.removeUser(annaUid);
        verify(fakeDataDao).selectUserByUserUid(annaUid);
        verify(fakeDataDao).deleteUserByUserUid(annaUid);
        assertThat(deleteResult).isEqualTo(1);
    }

    @Test
    void shouldInsertUser() {
        UUID userUid = UUID.randomUUID();
        User anna = new User(null,"anna","montana", User.Gender.FEMALE,22,"anna.montana@gmail.com");
        given(fakeDataDao.insertUser(any(UUID.class),eq(anna))).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int insertionResult = userService.insertUser(anna);
        verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());

        User user = captor.getValue();

        assertAnnaFields(user);
        assertThat(insertionResult).isEqualTo(1);
    }
    private static void assertAnnaFields(User user) {
        assertThat(user.getFirstName()).isEqualTo("anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getEmail()).isEqualTo("anna.montana@gmail.com");
        assertThat(user.getUserUid()).isNotNull();
        assertThat(user.getUserUid()).isInstanceOf(UUID.class);
    }
}