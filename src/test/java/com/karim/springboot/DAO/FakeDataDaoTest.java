package com.karim.springboot.DAO;

import com.karim.springboot.model.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FakeDataDaoTest {
    @Autowired
    private FakeDataDao fakeDataDao;

    @Before
    void setUp() throws Exception{
        fakeDataDao = new FakeDataDao();

    }

    @Test
    void shouldSelectAllUsers() throws Exception {
       List<User> users = fakeDataDao.selectAllUsers();
       assertThat(users).hasSize(1);
       User user = users.get(0);
       assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getFirstName()).isEqualTo("Joe");
        assertThat(user.getLastName()).isEqualTo("Jones");
        assertThat(user.getGender()).isEqualTo(User.Gender.MALE);
        assertThat(user.getEmail()).isEqualTo("Joe.Jones@gmail.com");
        assertThat(user.getUserUid()).isNotNull();
    }

    @Test
    void selectUserByUserUid() {
        UUID joeUserId = fakeDataDao.selectAllUsers().get(0).getUserUid();
        Optional<User> joeOptional = fakeDataDao.selectUserByUserUid(joeUserId);

        assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
        assertThat(joeOptional.isPresent());
    }







    @Test
    void shouldNotSelectUserByRandomUserUid() {
      Optional<User> user = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
      assertThat(user.isPresent()).isFalse();
    }

    @Test
    void shouldUpdateUser() {
        UUID joeUserId = fakeDataDao.selectAllUsers().get(0).getUserUid();
        User joeUser = new User(joeUserId,"rosa","montana", User.Gender.FEMALE,30,"anna.montana@gmail.com");
        fakeDataDao.updateUser(joeUser);
        Optional<User> user = fakeDataDao.selectUserByUserUid(joeUserId);
        assertThat(user.isPresent()).isTrue();
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
        assertThat(user.get()).isEqualToComparingFieldByField(joeUser);
    }

    @Test
    void deleteUserByUserUid() {
        UUID joeUserId = fakeDataDao.selectAllUsers().get(0).getUserUid();
        fakeDataDao.deleteUserByUserUid(joeUserId);
        assertThat(fakeDataDao.selectUserByUserUid(joeUserId).isPresent()).isFalse();
        assertThat(fakeDataDao.selectAllUsers().isEmpty());
    }

    @Test
    void shouldInsertUser() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId,"anna","montana", User.Gender.FEMALE,30,"anna@gmail.com");
        fakeDataDao.insertUser(userId, user);
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(2);
        assertThat(fakeDataDao.selectUserByUserUid(userId).get()).isEqualToComparingFieldByField(user);
    }
}