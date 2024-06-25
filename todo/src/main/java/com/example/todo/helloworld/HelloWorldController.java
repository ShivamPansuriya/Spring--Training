package com.example.todo.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

    @RequestMapping("hello-world-jsp")
    public String helloWorld() {
        return "Hello";
    }
}
