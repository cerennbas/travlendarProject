package com.travlendar.springtravlendar.service;

import com.travlendar.springtravlendar.model.Meeting;
import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("meetingService")
public class MeetingServiceImpl implements MeetingService{
    @Autowired
    private MeetingRepository meetingRepository;

    @Override
    public Meeting save(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> findAllMeetingByUser(User user) {
        return meetingRepository.findByUser(user);
    }
}
