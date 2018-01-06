package com.travlendar.springtravlendar.service;

import com.travlendar.springtravlendar.model.Meeting;
import com.travlendar.springtravlendar.model.User;

import java.util.List;

public interface MeetingService {
    Meeting save(Meeting meeting);

    List<Meeting> findAllMeetingByUser(User user);
}
