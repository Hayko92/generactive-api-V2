package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.configuration.userService.CustomUserDetailService;
import com.example.generative_api_v2.db.jpaRepositories.UserRepository;
import com.example.generative_api_v2.model.Credentials;
import com.example.generative_api_v2.model.User;
import com.example.generative_api_v2.util.JwtTokenUtil;
import com.example.generative_api_v2.util.ResponseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController

public class LoginController {

    private final UserRepository userService;
    private final JwtTokenUtil jwtProvider;
    private final CustomUserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserRepository userService, JwtTokenUtil jwtProvider, CustomUserDetailService userDetailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseToken auth(@RequestBody Credentials request) {
        User userEntity = userDetailService.findByLoginAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getUsername());
        return new ResponseToken(token);
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid Credentials registrationRequest) {
        User u = new User();
        u.setPassword(registrationRequest.getPassword());
        u.setUsername(registrationRequest.getUsername());
        userDetailService.saveUser(u);
        return "you have successfully registered: Congratulations!!!";
    }
}
