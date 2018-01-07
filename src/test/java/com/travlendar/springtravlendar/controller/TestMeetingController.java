package com.travlendar.springtravlendar.controller;

import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.service.GoogleMapsService;
import com.travlendar.springtravlendar.service.MeetingService;
import com.travlendar.springtravlendar.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MeetingController.class, secure = false)
public class TestMeetingController {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MeetingService meetingService;

    @MockBean
    private GoogleMapsService googleMapsService;

    private User registeredUser;

    @Before
    public void setUp() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testGetAllMeetingHttpBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/user/allMeeting")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testAddMeetingHttpBadRequest() throws Exception {

        String meetingJson =  "{\"description\": \"toplanti\",\"location\": \"atasehir\",\"endTime\": " +
                "\"2018-01-07T19:00:00.000Z\",\"startTime\": \"2018-01-07T18:14:17.198Z\" }";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/addMeeting")
                .accept(MediaType.APPLICATION_JSON).content(meetingJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}
