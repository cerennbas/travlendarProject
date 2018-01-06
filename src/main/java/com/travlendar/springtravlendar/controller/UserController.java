package com.travlendar.springtravlendar.controller;

import com.travlendar.springtravlendar.authentication.LoginResponse;
import com.travlendar.springtravlendar.exception.TravlendarException;
import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="login", method = RequestMethod.POST)
    public LoginResponse login(HttpSession httpSession, @RequestBody Map<String, String> json) throws TravlendarException {
        final String email = json.get("email");
        final String password = json.get("password");

        if(email == null || password == null) {
            throw new TravlendarException("Email and Password must be given.", HttpStatus.BAD_REQUEST);
        }

        if(!userService.validatePassword(email, password)) {
            throw new TravlendarException("Invalid login! Please check your password.", HttpStatus.UNAUTHORIZED);
        }

        httpSession.setAttribute("email", email);
        return new LoginResponse(Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretKey").compact());
    };

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        String email = user.getEmail();
        if(userService.findUserByEmail(user.getEmail()) != null)  {
            return ResponseEntity.badRequest().body("This email address is already exist. You cannot be registered with this email.");
        }

        userService.save(user);
        return ResponseEntity.ok().body("Thank you for registration! You will redirecting to login page.");
    }

    @RequestMapping(value="/logout", method = RequestMethod.POST)
    public ResponseEntity<?> login(HttpSession httpSession) {
        httpSession.invalidate();
        return ResponseEntity.ok().body("Successfully logged out.");
    }
}
