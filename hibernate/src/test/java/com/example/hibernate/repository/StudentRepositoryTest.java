package com.example.hibernate.repository;

import com.example.hibernate.entity.Passport;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

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
	public void retrivePassportAssociatedWithStudent(){
		var passport = entityManager.find(Passport.class, 40001L);
		logger.info("Passport: {}", passport);
		logger.info("passport owner->  Student: {}", passport.getStudent());
	}
}
