package com.example.generative_api_v2.servlet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConotroler {
    @GetMapping("/")
    public String test() {
        return "it wroks!!";
    }
}
