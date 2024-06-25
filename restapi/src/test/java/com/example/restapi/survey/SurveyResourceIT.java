package com.example.restapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SurveyResourceIT {
    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

    private static String GENERIC_QUESTIONS_URL = "/surveys/Survey1/questions";

    @Autowired
    private TestRestTemplate template;

    @Test
    @Order(1)
    void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException
    {
        ResponseEntity<String> response = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        String expectedResponse =
                """
                {
                    "id":"Question1",
                    "description":"Most Popular Cloud Platform Today",
                    "correctAnswer":"AWS"
                }
                """;


        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertEquals("application/json", response.getHeaders().getContentType().toString());

        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    @Order(3)
    void retrieveAllSurveyQuestion_basicScenario() throws JSONException {
        ResponseEntity<String> response = template.getForEntity(GENERIC_QUESTIONS_URL, String.class);

        String expectedResponse =
                """
                        [
                          {
                            "id": "Question1"
                          },
                          {
                            "id": "Question2"
                          },
                          {
                            "id": "Question3"
                          }
                        ]
                
                """;


        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertEquals("application/json", response.getHeaders().getContentType().toString());

        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    @Order(2)
    void addNewSurveyQuestion_basicScenario()
    {
        String requestBody = """
					{
					  "description": "Your Favorite Language",
					  "options": [
					    "Java",
					    "Python",
					    "JavaScript",
					    "Haskell"
					  ],
					  "correctAnswer": "Java"
					}
				""";

        var header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        var entity= new HttpEntity<String>(requestBody, header);

        var response = template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.POST,entity, String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        var location = response.getHeaders().getLocation();

        assertTrue(response.getHeaders().getLocation().toString().contains(GENERIC_QUESTIONS_URL));

        template.delete(location);

    }
}
