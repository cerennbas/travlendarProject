package com.travlendar.springtravlendar.controller;

import com.travlendar.springtravlendar.exception.TravlendarException;
import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.service.EmailService;
import com.travlendar.springtravlendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/retrievePassword", method = RequestMethod.POST)
    public ResponseEntity retrievePassword(@RequestBody Map<String, String> json,
                                           HttpServletRequest request) {
        String message;
        String userEmail = json.get("email");
        User user = userService.findUserByEmail(userEmail);
        if(user == null) {
            throw new TravlendarException("We couldn't find any account for given e-mail address.", HttpStatus.NOT_FOUND);
        }  else {
            user.setResetToken(UUID.randomUUID().toString());

            userService.save(user);
            String applicationURL = request.getScheme() + "://" + request.getServerName();

            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("support@travlendar.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Retrieve Request");

            passwordResetEmail.setText("We heard you forgot your Travlendar account password. " +
                    "Please click the link below to reset your password and sign in to your account\n" +
                    applicationURL + "/reset?token=" + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);

            message = "A password reset link has been sent to " + userEmail;
        }

        return ResponseEntity.ok().body(message);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ResponseEntity displayResetPasswordPage(@RequestParam("token") String token) {
        User user = userService.findUserByResetToken(token);

        if (user != null) {
            return ResponseEntity.ok().body("You can change your password");
        } else {
            throw new TravlendarException("This is an invalid password reset "
                    + "link.", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity setNewPassword(@RequestParam("token") String token, @RequestBody Map<String, String> requestParams) {
        User user = userService.findUserByResetToken(token);

        if (user != null) {
            user.setPassword(requestParams.get("password"));
            user.setResetToken(null);
            userService.save(user);

            return ResponseEntity.ok().body( "You have successfully " +
                    "reset your password. You may now login.");

        } else {
            throw new TravlendarException("This is an invalid password reset "
                + "link.", HttpStatus.BAD_REQUEST);
        }
    }
}
