package com.test.scheduler.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {
        return "API working!";
    }

    @GetMapping("/error")
    public String testError() {
        throw new RuntimeException("Test exception!");
    }
}