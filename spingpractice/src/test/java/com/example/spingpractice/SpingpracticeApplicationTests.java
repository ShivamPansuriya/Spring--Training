package com.example.spingpractice;

import com.example.spingpractice.jackson.ApiService;
import com.example.spingpractice.jackson.Course;
import com.example.spingpractice.jackson.CourseJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@WebMvcTest
class SpingpracticeApplicationTests {

	@MockBean
	CourseJpaRepository courseJpaRepository;

	@MockBean
	ApiService apiService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {

		var course = new Course("maths","shivam");
		course.setNumber(1);
		List<Object> result = new ArrayList<>();
		result.add(course);
		when(apiService.groupByName("courseName")).thenReturn(result);
		ObjectWriter object = new ObjectMapper().writer().withDefaultPrettyPrinter();

		mockMvc.perform(get("/api/group/courseName"))
				.andExpect(status().isOk())
				.andExpect(content().json(object.writeValueAsString(result)));
	}

}
