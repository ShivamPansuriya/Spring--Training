package com.example.spingpractice.jackson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
interface CourseRepository extends JpaRepository<Course,Integer>{
    public List<Course> findCourseByCourseName(String courseName);
}
