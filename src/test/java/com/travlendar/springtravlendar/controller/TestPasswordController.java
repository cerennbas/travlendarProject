package com.travlendar.springtravlendar.controller;

import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.service.EmailService;
import com.travlendar.springtravlendar.service.GoogleMapsService;
import com.travlendar.springtravlendar.service.MeetingService;
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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PasswordController.class, secure = false)
public class TestPasswordController {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private GoogleMapsService googleMapsService;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setEmail("user1@user.com");
        user.setFirstName("username");
        user.setLastName("lastname");
        user.setPassword("passuser123");
        user.setHasPersonalVehicle(false);

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testRetrievePasswordHttpOk() throws Exception {
        String emailJson =  "{\"email\": \"user1@user.com\"}";

        when(userService.findUserByEmail("user1@user.com")).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/retrievePassword")
                .accept(MediaType.APPLICATION_JSON).content(emailJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testRetrievePasswordHttpNotFound() throws Exception {
        String emailJson =  "{\"email\": \"user2@user.com\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/retrievePassword")
                .accept(MediaType.APPLICATION_JSON).content(emailJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}
