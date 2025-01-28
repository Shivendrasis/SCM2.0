package com.scm20.services;

import java.util.List;
import java.util.Optional;

import com.scm20.entities.User;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    List<User> getAllUser();

    User getUserByEmail(String email);
}
