package com.travlendar.springtravlendar.repository;

import com.travlendar.springtravlendar.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    User findByEmail(String email);

    User save(User user);

    User findUserByResetToken(String token);
}
