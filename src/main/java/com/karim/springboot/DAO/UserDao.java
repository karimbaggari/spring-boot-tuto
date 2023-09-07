package com.karim.springboot.DAO;

import com.karim.springboot.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    List<User> getAllUsers();

    User getUser(UUID userUid);

    int updateUser(User user);

    int removeUser(UUID userUid);


    int insertUser(UUID userUid,User user);
}
