package com.travlendar.springtravlendar.controller;

import com.travlendar.springtravlendar.exception.TravlendarException;
import com.travlendar.springtravlendar.model.Meeting;
import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.service.MeetingService;
import com.travlendar.springtravlendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MeetingController {
    @Autowired
    private UserService userService;
    @Autowired
    private MeetingService meetingService;

    @RequestMapping(value="/user/allMeeting")
    public ResponseEntity<List<Meeting>> getMeetings() {
        //meetingService.findAllMeetingByUser(user);
        List<Meeting> meetings = new ArrayList<>();
        return ResponseEntity.ok().body(meetings);
    }

    @RequestMapping(value="/addMeeting", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(HttpSession httpSession, @RequestBody Meeting meeting) throws TravlendarException {
        String email = (String) httpSession.getAttribute("email");

        if(email == null) {
            throw new TravlendarException("no session found", HttpStatus.BAD_REQUEST);
        }
        User user = userService.findUserByEmail(email);

        meeting.setUser(user);
        meetingService.save(meeting);

        return ResponseEntity.ok().body(meeting);
    }

    @RequestMapping(value="/{user}/deleteMeeting/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteMeeting(@PathVariable("user") String user, @PathVariable("id")String meetingId, @RequestBody Meeting meeting) {
        return ResponseEntity.ok().body("Meeting deleted for "+ user);
    }

    @RequestMapping(value="/{user}/editMeeting/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> editMeeting(@PathVariable("user") String user, @RequestBody Meeting meeting) {
        return ResponseEntity.ok().body("Meeting added for "+ user);
    }

}
