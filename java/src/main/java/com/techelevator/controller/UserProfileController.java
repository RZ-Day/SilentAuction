package com.techelevator.controller;

import com.techelevator.model.User;
import com.techelevator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
public class UserProfileController {
    private final UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/contact")
    public ResponseEntity<String> updateContactInformation(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String contactInformation = request.get("contactInformation");
        userService.updateUserContactInformation(username, contactInformation);
        return ResponseEntity.ok("Contact Information updated.");
    }

    @PutMapping("/anonymous")
    public ResponseEntity<String> updateAllowAnonymous(@RequestBody Map<String, Object> request) {
        String username = (String)request.get("username");
        boolean allowAnonymous = (boolean) request.get("allowAnonymous");
        userService.updateAllowAnonymous(username, allowAnonymous);
        return ResponseEntity.ok("Anonymous Setting updated.");

    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserProfile(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
