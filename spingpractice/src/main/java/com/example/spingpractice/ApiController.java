package com.example.spingpractice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ApiController {

    @RequestMapping("/course")
    public List<Course> course() {
        return Arrays.asList( new Course(1,"Shivam","science"),
                new Course(1,"Raj","science")
        );
    }
}
