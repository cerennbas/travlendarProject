package com.travlendar.springtravlendar.service;

import com.travlendar.springtravlendar.model.User;

import java.util.List;

public interface UserService {
    User save(User user);

    List<User> findAllUsers();

    boolean validatePassword(String email, String password);

    User findUserByEmail(String email);

    User findUserByResetToken(String resetToken);
}
