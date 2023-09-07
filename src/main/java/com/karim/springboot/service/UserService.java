package com.karim.springboot.service;

import com.karim.springboot.DAO.FakeDataDao;
import com.karim.springboot.DAO.UserDao;
import com.karim.springboot.model.User;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    public Optional<User> getUser(UUID userUid) {
        return userDao.selectUserByUserUid(userUid);
    }


    public int updateUser(User user) {
        Optional <User> optionalUser = getUser(user.getUserUid());
        if(optionalUser.isPresent()) {
            userDao.updateUser(user);
            return 1;
        }else {
            return -1;
        }
    }


    public int removeUser(UUID userUid) {
        Optional <User> optionalUser = getUser(userUid);
        if(optionalUser.isPresent()) {
            userDao.deleteUserByUserUid(userUid);
            return 1;
        } else {
            return -1;
        }
    }


    public int insertUser(UUID userUid,User user) {
        return userDao.insertUser(UUID.randomUUID(),user);
    }
}
