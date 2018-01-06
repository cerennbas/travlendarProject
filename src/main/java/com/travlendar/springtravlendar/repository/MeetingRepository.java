package com.travlendar.springtravlendar.repository;

import com.travlendar.springtravlendar.model.Meeting;
import com.travlendar.springtravlendar.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {
    List<Meeting> findAll();

    List<Meeting> findByUser(User user);

    Meeting save(Meeting meeting);
}
