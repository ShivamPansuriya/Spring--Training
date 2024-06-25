package com.example.jpahibernate.courses.springdatajpa;

import com.example.jpahibernate.courses.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSpringDataJpaRepository extends JpaRepository<Course, Long> {

}
