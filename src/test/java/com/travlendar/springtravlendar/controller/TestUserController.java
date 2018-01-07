package com.travlendar.springtravlendar.controller;


import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class TestUserController {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User registeredUser;

    @Before
    public void setUp() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();

        registeredUser = new User();

        registeredUser.setEmail("user@user.com");
        registeredUser.setFirstName("username");
        registeredUser.setLastName("lastname");
        registeredUser.setPassword("passuser123");
        registeredUser.setHasPersonalVehicle(false);
    }

    @Test
    public void testRegisterHttpOK() throws Exception {
        User user = new User();
        user.setEmail("user1@user.com");
        user.setFirstName("username");
        user.setLastName("lastname");
        user.setPassword("passuser123");
        user.setHasPersonalVehicle(false);

        String userJson = "{\"email\":\"user1@user.com\",\"firstName\":\"username\",\"lastName\":\"lastname\",\"password\":\"passuser123\",\"hasPersonalVehicle\":false}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/register")
                .accept(MediaType.APPLICATION_JSON).content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testRegisterWithAlreadyRegisteredEmailHttpBadRequest() throws Exception {
        User user = new User();
        user.setEmail("user@user.com");
        user.setFirstName("username");
        user.setLastName("lastname");
        user.setPassword("passuser123");
        user.setHasPersonalVehicle(false);

        when(userService.findUserByEmail("user@user.com")).thenReturn(registeredUser);

        String userJson = "{\"email\":\"user@user.com\",\"firstName\":\"username\",\"lastName\":\"lastname\",\"password\":\"passuser123\",\"hasPersonalVehicle\":false}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/register")
                .accept(MediaType.APPLICATION_JSON).content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testRegisterWithNoEmailAndPasswordHttpBadRequest() throws Exception {
        User user = new User();
        user.setFirstName("username");
        user.setLastName("lastname");
        user.setHasPersonalVehicle(false);

        String userJson = "{\"firstName\":\"username\",\"lastName\":\"lastname\",\"hasPersonalVehicle\":false}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/register")
                .accept(MediaType.APPLICATION_JSON).content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testLoginHttpOK() throws Exception {
        String userJson = "{\"email\":\"user@user.com\",\"password\":\"passuser123\"}";

        when(userService.validatePassword(anyString(), anyString())).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/login")
                .accept(MediaType.APPLICATION_JSON).content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testLoginInvalidPasswordHttpBadRequest() throws Exception {
        String userJson = "{\"email\":\"user@user.com\"}";

        when(userService.validatePassword(anyString(), anyString())).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/login")
                .accept(MediaType.APPLICATION_JSON).content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testLoginInvalidPasswordHttpUnauthorized() throws Exception {
        String userJson = "{\"email\":\"user@user.com\",\"password\":\"invalidpass\"}";

        when(userService.validatePassword(anyString(), anyString())).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/login")
                .accept(MediaType.APPLICATION_JSON).content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    public void testLogoutHttpOK() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/logout");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
