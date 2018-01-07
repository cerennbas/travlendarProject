package com.travlendar.springtravlendar.controller;

import com.travlendar.springtravlendar.exception.TravlendarException;
import com.travlendar.springtravlendar.model.Meeting;
import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.service.MeetingService;
import com.travlendar.springtravlendar.service.UserService;
import com.travlendar.springtravlendar.service.GoogleMapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MeetingController {
    @Autowired
    private UserService userService;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private GoogleMapsService googleMapsService;

    @RequestMapping(value="/user/allMeeting")
    public ResponseEntity<List<Meeting>> getMeetings(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");

        if(email == null) {
            throw new TravlendarException("no session found", HttpStatus.BAD_REQUEST);
        }

        User user = userService.findUserByEmail(email);

        List<Meeting> meetings =  meetingService.findAllMeetingByUser(user);
        return ResponseEntity.ok().body(meetings);
    }

    @RequestMapping(value="/addMeeting", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(HttpSession httpSession, @RequestBody Meeting meeting) throws Exception {
        String email = (String) httpSession.getAttribute("email");

        if(email == null) {
            throw new TravlendarException("no session found", HttpStatus.BAD_REQUEST);
        }
        User user = userService.findUserByEmail(email);

        meeting.setUser(user);
        String duration = googleMapsService.travelTime(meeting.getStartLocation(), meeting.getLocation());
        meeting.setDuration(duration);

        meetingService.save(meeting);

        return ResponseEntity.ok().body(duration);
    }

    /**
    @RequestMapping(value="/deleteMeeting", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMeeting(HttpSession session, @RequestBody Meeting meeting) {
        meetingService.de
    }**/

    /**
    @RequestMapping(value="/editMeeting/", method = RequestMethod.POST)
    public ResponseEntity<?> editMeeting(HttpSession httpSession, @RequestBody Meeting meeting) {
        String email = (String) httpSession.getAttribute("email");

        if(email == null) {
            throw new TravlendarException("no session found", HttpStatus.BAD_REQUEST);
        }
        User user = userService.findUserByEmail(email);

        meeting.setUser(user);
        meetingService.save(meeting);
    }**/
}
