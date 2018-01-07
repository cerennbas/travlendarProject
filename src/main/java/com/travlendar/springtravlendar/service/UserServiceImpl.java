package com.travlendar.springtravlendar.service;

import com.travlendar.springtravlendar.exception.TravlendarException;
import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements  UserService {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean validatePassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new TravlendarException("Invalid login! Please check your password.", HttpStatus.UNAUTHORIZED);
        }
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByResetToken(String resetToken) {
        return userRepository.findUserByResetToken(resetToken);
    }
}
