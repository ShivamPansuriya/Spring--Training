package com.example.hibernate;

import com.example.hibernate.entity.Review;
import com.example.hibernate.repository.CourseRepository;
import com.example.hibernate.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class HibernateApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(HibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("Data of Course 10001: {}", courseRepository.getById(10001L));

//		studentRepository.saveStudentWithPassport();
//		courseRepository.deleteById(10001L);
		var reviews = new ArrayList<Review>();
		reviews.add(new Review("5","awasome"));
		reviews.add(new Review("5","very good"));
		courseRepository.addReviewsForCourse(10003L, reviews);

	}
}
