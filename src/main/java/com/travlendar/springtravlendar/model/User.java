package com.travlendar.springtravlendar.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@Table(name = "USERS")
public class User {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "EMAIL", unique = true)
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name =  "LAST_NAME")
    private String lastName;
    @Column(name =  "HOME_ADDRESS")
    private String homeAddress;
    @Column(name =  "WORK_ADDRESS")
    private String workAddress;
    @Column(name =  "HAS_PERSONAL_VEHICLE")
    private boolean hasPersonalVehicle;
    @Column(name =  "PASSWORD")
    private String password;
    @Column(name = "TOKEN")
    private String resetToken;

    @PrePersist
    public void encodePassword(){
        password = bCryptPasswordEncoder.encode(password);
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meeting> meetings = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public boolean isHasPersonalVehicle() {
        return hasPersonalVehicle;
    }

    public void setHasPersonalVehicle(boolean hasPersonalVehicle) {
        this.hasPersonalVehicle = hasPersonalVehicle;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
