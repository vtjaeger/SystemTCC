package com.tcc.controller;

import com.tcc.dtos.request.user.LoginRequest;
import com.tcc.dtos.request.user.UserRequest;
import com.tcc.dtos.response.user.TokenResponse;
import com.tcc.models.User;
import com.tcc.repository.UserRepository;
import com.tcc.security.TokenService;
import com.tcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity addUser(@RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @GetMapping
    public ResponseEntity getUsers(){
        return userService.getUsers();
    }

    @PatchMapping("/{id}")
    public ResponseEntity inactive(@PathVariable Long id){
        return userService.inactiveOrActive(id);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/excel")
    public ResponseEntity generateExcel() throws IOException {
        return userService.generateExcel();
    }
}
