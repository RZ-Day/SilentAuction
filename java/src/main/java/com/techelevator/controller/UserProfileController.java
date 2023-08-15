package com.techelevator.controller;

import com.techelevator.model.User;
import com.techelevator.model.UserProfileDto;
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

    @PutMapping("/update")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfileDto userProfile) {
        String username = userProfile.getUsername();
        if (userProfile.getPhone() != null) {
            userService.updateUserPhone(username, userProfile.getPhone());
            return ResponseEntity.ok("Phone Number updated.");
        } else if (userProfile.getEmail() != null) {
            userService.updateUserEmail(username, userProfile.getEmail());
            return ResponseEntity.ok("Email Address updated.");
        } else if (userProfile.getBillingAddress() != null) {
            userService.updateUserBillingAddress(username, userProfile.getBillingAddress());
            return ResponseEntity.ok("Billing Address updated.");
        } else if (userProfile.getShippingAddress() != null) {
            userService.updateUserBillingAddress(username, userProfile.getShippingAddress());
            return ResponseEntity.ok("Shipping Address updated.");
        } else if (userProfile.isAllowAnonymous()) {
            // TODO: allow this change later
            userService.updateAllowAnonymous(username, userProfile.isAllowAnonymous());
            return ResponseEntity.ok("Anonymous Setting updated.");
        } else {
            return ResponseEntity.badRequest().body("No valid parameters provided.");
        }
    }

    @GetMapping("/")
    public ResponseEntity<UserProfileDto> getUserProfile(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.setUserId(user.getUserId());
            userProfileDto.setFullName(user.getFullName());
            userProfileDto.setPhone(user.getPhone());
            userProfileDto.setUsername(user.getUsername());
            userProfileDto.setEmail(user.getEmail());
            userProfileDto.setBillingAddress(user.getBillingAddress());
            userProfileDto.setShippingAddress(user.getShippingAddress());
            userProfileDto.setAllowAnonymous(user.isAllowAnonymous());
            return ResponseEntity.ok(userProfileDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
