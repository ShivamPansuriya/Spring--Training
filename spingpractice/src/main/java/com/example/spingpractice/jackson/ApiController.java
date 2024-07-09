package com.example.spingpractice.jackson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    ApiService apiService;



    @RequestMapping("/api")
    public List<Course> getCourse() {
        return apiService.getAllCourses();
    }

    @RequestMapping("/api/{name}/{courseName}")
    public List<Course> getCourseByNames(@PathVariable String name, @PathVariable String courseName) {
        return apiService.getCourseByNames(name,courseName);
    }

    @GetMapping("/api/group-by-courses")
    public ResponseEntity<Map<String,List<String>>> getStudentListGroupByCourse()
    {
        return new ResponseEntity<>(apiService.getStudentListGroupByCourse(), HttpStatus.OK);
    }

    @GetMapping("/api/group/{variable}")
    public List<Object> groupByName(@PathVariable String variable) {
        return apiService.groupByName(variable);
    }
}
