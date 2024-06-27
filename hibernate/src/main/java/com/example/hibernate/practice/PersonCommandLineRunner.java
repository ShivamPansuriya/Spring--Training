package com.example.hibernate.practice;

import com.example.hibernate.practice.entity.Person;
import com.example.hibernate.practice.jpa.PersonJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.util.Date;

//@Component
public class PersonCommandLineRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
    PersonJpaRepository repository;

    @Override
    public void run(String... args) throws Exception {
        logger.info("User id 10001 -> {}", repository.findById(10001));

        logger.info("Inserting -> {}",
                repository.insert(new Person("Tara", "Berlin", new Date())));

        logger.info("Update 10003 -> {}",
                repository.update(new Person(10003, "Pieter", "Utrecht", new Date())));

        repository.deleteById(10002);

//        logger.info("All users -> {}", repository.findAll());
    }
}
