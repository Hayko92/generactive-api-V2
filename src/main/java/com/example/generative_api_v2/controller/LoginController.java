package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.db.jpaRepositories.UserRepository;
import com.example.generative_api_v2.model.Credentials;
import com.example.generative_api_v2.model.User;
import com.example.generative_api_v2.util.JwtTokenUtil;
import com.example.generative_api_v2.util.ResponseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userService;
    private final JwtTokenUtil jwtProvider;

    @Autowired
    public LoginController(UserRepository userService, JwtTokenUtil jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping
    public ResponseToken auth(@RequestBody Credentials request) {
        User userEntity = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getUsername());
        return new ResponseToken(token);
    }
}
