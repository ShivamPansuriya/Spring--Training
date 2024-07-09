package com.example.spingpractice.jackson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseJpaRepository courseJpaRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCourseByNames(String name, String courseName) {
        var result = courseJpaRepository.getByUserAndCourse(name, courseName);
        System.out.println(result);
        return result;
    }

    public Map<String, List<String>> getStudentListGroupByCourse() {
        var courses = courseRepository.findAll();
        var groupByCourse = courses.stream().collect(Collectors.groupingBy(Course::getCourseName));

        Map<String, List<String>> result = new HashMap<>();

        for (var key : groupByCourse.keySet()) {
            var list = groupByCourse.get(key);
            List<String> nameList = new ArrayList<>();
            for (var course : list) {
                nameList.add(course.getName());
            }
            result.put(key, nameList);
        }
        return courses.stream()
                .collect(Collectors.groupingBy(Course::getCourseName,
                        Collectors.mapping((course) -> course.getName(), Collectors.toList())));

    }

    public List<Object> groupByName(String variable) {
        //        System.out.println(result);
        return courseJpaRepository.groupByName(variable);
    }
}
