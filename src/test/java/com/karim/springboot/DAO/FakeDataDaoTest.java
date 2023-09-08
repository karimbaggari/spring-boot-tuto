package com.karim.springboot.DAO;

import com.karim.springboot.model.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserByUserUid() {
    }

    @Test
    void insertUser() {
    }
}