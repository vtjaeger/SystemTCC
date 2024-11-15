package com.tcc.service;

import com.tcc.models.User;
import com.tcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity getAllUsers(){
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok().body(userList);
    }
}
