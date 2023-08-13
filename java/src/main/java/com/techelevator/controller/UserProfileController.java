package com.techelevator.controller;

import com.techelevator.model.User;
import com.techelevator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin (origins = "http://localhost:8080")
@RestController
@RequestMapping("api/profile")
public class UserProfileController {
    private final UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<String> updateUserProfile(@RequestParam String username,
                                                    @RequestParam(required = false) String phone,
                                                    @RequestParam(required = false) String email,
                                                    @RequestParam(required = false) String address,
                                                    @RequestParam(required = false) Boolean allowAnonymous) {
        if (phone != null) {
            userService.updateUserPhone(username, phone);
            return ResponseEntity.ok("Phone Number updated.");
        } else if (email != null) {
            userService.updateUserEmail(username, email);
            return ResponseEntity.ok("Email Address updated.");
        } else if (address != null) {
            userService.updateUserAddress(username, address);
            return ResponseEntity.ok("Address updated.");
        } else if (allowAnonymous != null) {
            userService.updateAllowAnonymous(username, allowAnonymous);
            return ResponseEntity.ok("Anonymous Setting updated.");
        } else {
            return ResponseEntity.badRequest().body("No valid parameters provided.");
        }
    }

    @GetMapping
    public ResponseEntity<User> getUserProfile(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
