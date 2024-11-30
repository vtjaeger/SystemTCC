package com.tcc.controller;

import com.tcc.dtos.request.user.LoginRequest;
import com.tcc.dtos.request.user.UserRequest;
import com.tcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity addUser(@RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @GetMapping
    public ResponseEntity getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/login")
    public ResponseEntity login(LoginRequest dto){
        return userService.login(dto);
    }
}
