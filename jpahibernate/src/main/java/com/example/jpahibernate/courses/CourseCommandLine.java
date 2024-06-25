package com.example.jpahibernate.courses;

import com.example.jpahibernate.courses.jdbc.CourseJdbcRepository;
import com.example.jpahibernate.courses.jpa.CourseJpaRepository;
import com.example.jpahibernate.courses.springdatajpa.CourseSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseCommandLine implements CommandLineRunner {

//    @Autowired
//    private CourseJdbcRepository repository;

//    @Autowired
//    private CourseJpaRepository repository;

    @Autowired
    private CourseSpringDataJpaRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.save(new Course(1, "Learn AWS Jpa!", "in28minutes"));
        repository.save(new Course(2, "Learn Azure Spring Jpa!", "in28minutes"));
        repository.save(new Course(3, "Learn DevOps Jpa!", "in28minutes"));

        repository.deleteById(1l);

        System.out.println(repository.findById(2l));
        System.out.println(repository.findById(3l));

//        System.out.println(repository.findAll());
//        System.out.println(repository.count());

//        System.out.println(repository.findByAuthor("in28minutes"));
//        System.out.println(repository.findByAuthor(""));
//
//        System.out.println(repository.findByName("Learn AWS Jpa!"));
//        System.out.println(repository.findByName("Learn DevOps Jpa!"));
    }
}
