package com.example.hibernate.relationaltables.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CourseRepositoryTest {

	@Autowired
	private CourseRepository courseRepository;

	@Test
	void getById_BasicTest() {
		var course = courseRepository.getById(10001L);

		assertEquals("Science", course.getName());
		assertEquals(10001L, course.getId());
	}

	@Test
	@DirtiesContext
	void deleteById_BasicTest() {
		courseRepository.deleteById(10002L);
		assertNull(courseRepository.getById(10002L));
	}

	@Test
	@DirtiesContext
	void save_BasicTest() {
		var course = courseRepository.getById(10002L);
		assertEquals("Maths", course.getName());

		course.setName("Maths- updated");
		courseRepository.save(course);

		var courseUpdated = courseRepository.getById(10002L);
		assertEquals("Maths- updated", courseUpdated.getName());
	}



}
