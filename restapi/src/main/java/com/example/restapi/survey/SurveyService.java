package com.example.restapi.survey;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyService {
    private static final List<Survey> surveys = new ArrayList<>();

    static {

        var question1 = new Question("Question1", "Most Popular Cloud Platform Today",
                List.of("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        var question2 = new Question("Question2", "Fastest Growing Cloud Platform",
                List.of("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        var question3 = new Question("Question3", "Most Popular DevOps Tool",
                List.of("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(List.of(question1, question2, question3));

        var survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

        surveys.add(survey);

    }

    public List<Survey> getAllSurveys() {
        return surveys;
    }

    public Survey getSurveyById(String id) {
        return surveys.stream().filter(survey -> survey.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Question> getQuestionsBySurveyId(String surveyId) {
        var survey = getSurveyById(surveyId);

        if(survey == null) return null;

        return survey.getQuestions();
    }

    public Question getQuestionOfSurveyId(String surveyId, String questionId) {
        var survey = getSurveyById(surveyId);

        if(survey == null) return null;

        var question = survey.getQuestions().stream().filter(question1 -> question1.getId().equals(questionId)).findFirst().orElse(null);

        return question;
    }

    public String addNewSurveyQuestion(String surveyId, Question question) {
        question.setId(generateRandomId());
        getQuestionsBySurveyId(surveyId).add(question);
        return question.getId();
    }


    public String deleteSurveyQuestion(String surveyId, String questionId) {

        List<Question> surveyQuestions = getQuestionsBySurveyId(surveyId);

        if (surveyQuestions == null)
            return null;

        boolean removed = surveyQuestions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));

        if(!removed) return null;

        return questionId;
    }

    public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
        List<Question> questions = getQuestionsBySurveyId(surveyId);
        questions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
        questions.add(question);
    }

    private String generateRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        String randomId = new BigInteger(32, secureRandom).toString();
        return randomId;
    }
}
