package com.techelevator.controller;

import com.techelevator.dao.UserProfileDao;
import com.techelevator.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/profile")
public class UserProfileController {
    private final UserProfileDao userProfileDao;

    @Autowired
    public UserProfileController(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfile userProfile) {
        String username = userProfile.getUsername();
        try {
            UserProfile result = userProfileDao.updateUserProfile(userProfile);
            return ResponseEntity.ok("User profile updated.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<UserProfile> getUserProfile(@RequestParam String username) {
        UserProfile user = userProfileDao.getUserProfileByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
