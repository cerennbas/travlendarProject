package com.travlendar.springtravlendar.model;

import javax.persistence.*;
import java.util.Date;

@Entity

public class Meeting {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "STARTLOCATION")
    private String startLocation;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DURATION")
    private String duration;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
