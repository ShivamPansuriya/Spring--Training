package com.example.hibernate.relationaltables.repository;

import com.example.hibernate.relationaltables.entity.Course;
import com.example.hibernate.relationaltables.entity.Passport;
import com.example.hibernate.relationaltables.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	@Transactional
	void fetch_BasicTest() {
		var student = studentRepository.getById(20001L);
		logger.info("Student: {}", student.getName());
		logger.info("Student passport: {}", student.getPassport());
	}

	@Test
	@Transactional
	public void retrievePassportAssociatedWithStudent(){
		var passport = entityManager.find(Passport.class, 40001L);
		logger.info("Passport: {}", passport);
		logger.info("passport owner->  Student: {}", passport.getStudent());
	}

	@Test
	@Transactional
	public void retrieveStudentAndCourseAssociatedWithStudent(){
		var student = entityManager.find(Student.class, 20001L);
		logger.info("Student: {}", student);
		logger.info("Courses enrolled->  {}", student.getCourses());
	}

	@Test
	public void JpqlQueryCourseWithoutStudents(){
		var result = entityManager.createQuery("select c from Course c where c.students is empty ", Course.class).getResultList();
		logger.info("Courses without student->  {}", result);
	}
}
