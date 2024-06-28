package com.example.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello-admin")
    public String adminHello() {
        return "Hello Admin";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello-user")
    public String userHello() {
        return "Hello User";
    }
}
