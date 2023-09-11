package com.karim.springboot.Service;

import com.karim.springboot.DAO.UserDao;
import com.karim.springboot.Model.User;
import org.springframework.stereotype.Service;
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


    public int insertUser(User user) {
        UUID userUid = UUID.randomUUID();
        user.setUserUid(userUid);
        return userDao.insertUser(UUID.randomUUID(),user);
    }
}
