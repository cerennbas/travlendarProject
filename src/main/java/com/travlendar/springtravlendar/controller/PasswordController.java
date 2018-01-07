package com.travlendar.springtravlendar.controller;

import com.travlendar.springtravlendar.model.User;
import com.travlendar.springtravlendar.service.EmailService;
import com.travlendar.springtravlendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api")
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
            message =  "We couldn't find any account for given e-mail address.";
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
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        User user = userService.findUserByResetToken(token);

        if (user != null) {
            modelAndView.addObject("resetToken", token);
        } else {
            modelAndView.addObject("errorMessage", "This is an invalid password reset "
                    + "link.");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams,
                                       RedirectAttributes redirectAttributes) {

        User user = userService.findUserByResetToken(requestParams.get("token"));

        if (user != null) {
            user.setPassword(requestParams.get("password"));
            user.setResetToken(null);
            userService.save(user);

            redirectAttributes.addFlashAttribute("successMessage", "You have successfully " +
                    "reset your password.  You may now login.");

            modelAndView.setViewName("redirect:login");
            return modelAndView;

        } else {
            modelAndView.addObject("errorMessage", "This is an invalid password" +
                    " reset link.");
            modelAndView.setViewName("resetPassword");
        }

        return modelAndView;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:login");
    }
}
