package com.realestate.platform.controller;

import com.realestate.platform.dto.UserDto;
import com.realestate.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserDto userDto) {
        return userService.loginAndGetToken(userDto);
    }

    @GetMapping("/authenticate")
    public Boolean authenticate(@RequestHeader("Authorization") String header) {
        return userService.authenticate(header);
    }

    @GetMapping("/admin")
    public String adminRoute() {
        return "Hello, admin!";
    }

    @GetMapping("/user")
    public String userRoute() {
        return "Hello, user!";
    }
}
