package com.travlendar.springtravlendar.service;

import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUserServiceImpl {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @Before
    public void setUp(){
        user = new User();
        user.setEmail("user@user.com");
        user.setFirstName("username");
        user.setLastName("lastname");
        user.setPassword("passuser123");
        user.setHasPersonalVehicle(false);
        user.setId(1);

        List<User> users = new ArrayList();

        users.add(user);

        Mockito.when(userRepository.findByEmail("user@user.com")).thenReturn(user);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        Mockito.when(userService.findAllUsers()).thenReturn(users);
        }

    @Test
    public void testSave() {
        User savedUser = userService.save(user);

        Assert.assertEquals(savedUser.getId(), 1);
        Assert.assertEquals(savedUser.getEmail(), "user@user.com");
        Assert.assertEquals(savedUser.getFirstName(), "username");
        Assert.assertEquals(savedUser.getLastName(), "lastname");
        Assert.assertEquals(savedUser.isHasPersonalVehicle(), false);

    }

    @Test
    public void testFindUserByEmail() {
        User userByEmail = userService.findUserByEmail("user@user.com");

        Assert.assertEquals(userByEmail.getId(), 1);
        Assert.assertEquals(userByEmail.getEmail(), "user@user.com");
        Assert.assertEquals(userByEmail.getFirstName(), "username");
        Assert.assertEquals(userByEmail.getLastName(), "lastname");
        Assert.assertEquals(userByEmail.isHasPersonalVehicle(), false);
    }

    @Test
    public void testFindAllUsers() {
        List<User> allUsers = userService.findAllUsers();

        Assert.assertEquals(allUsers.size(), 1);
    }
}
