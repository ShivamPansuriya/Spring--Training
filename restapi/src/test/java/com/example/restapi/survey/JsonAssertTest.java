package com.example.restapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class JsonAssertTest {

    @Test
    void jsonAssert_learningBasics() throws JSONException {

        String expectedResponse =
                """
                {
                    "id":"Question1",
                    "description":"Most Popular Cloud Platform Today",
                    "correctAnswer":"AWS"
                }
                """;

        String actualResponse =
                """
                  {"id":"Question1",
                  "description":"Most Popular Cloud Platform Today",
                  "options":["AWS","Azure","Google Cloud","Oracle "],
                  "correctAnswer":"AWS"}
                """;

        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
        // if value = true
        // then all the value of actual output will be compared with expected output and ans will be [test - FAILS]
        // but
        // in case of value = false only files in expected output are compared with actual output
    }

}
