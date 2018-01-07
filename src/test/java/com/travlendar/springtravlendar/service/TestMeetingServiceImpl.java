package com.travlendar.springtravlendar.service;

import com.travlendar.springtravlendar.model.Meeting;
import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.repository.MeetingRepository;
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
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMeetingServiceImpl {
    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private User user;

    @InjectMocks
    private MeetingServiceImpl meetingService;

    private Meeting meeting;

    @Before
    public void setUp(){
        meeting = new Meeting();

        meeting.setUser(user);
        meeting.setDescription("Test Meeting");
        meeting.setStartTime(new Date());
        meeting.setEndTime(new Date());
        meeting.setLocation("Test Place");
        meeting.setId(1);

        List<Meeting> meetings = new ArrayList();

        meetings.add(meeting);

        Mockito.when(meetingRepository.findByUser(user)).thenReturn(meetings);

        Mockito.when(meetingRepository.save(meeting)).thenReturn(meeting);
    }

    @Test
    public void testSave() {
        Meeting savedMeeting = meetingService.save(meeting);

        Assert.assertEquals(meeting.getLocation(), "Test Place");
        Assert.assertEquals(meeting.getDescription(), "Test Meeting");
        Assert.assertEquals(meeting.getId(), 1);
        Assert.assertEquals(meeting.getUser(), user);
    }

    @Test
    public void testFindUserByEmail() {
        List<Meeting> allMeetingByUser = meetingService.findAllMeetingByUser(user);

        Assert.assertEquals(allMeetingByUser.size(), 1);
    }
}
