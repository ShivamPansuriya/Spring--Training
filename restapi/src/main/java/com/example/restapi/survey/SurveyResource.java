package com.example.restapi.survey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.beans.ConstructorProperties;
import java.util.List;

@RestController
public class SurveyResource {

    private final SurveyService surveyService;

    private MessageSource messageSource;

    public SurveyResource(SurveyService surveyService, MessageSource messageSource) {
        this.surveyService = surveyService;
        this.messageSource = messageSource;
    }

    @RequestMapping("/surveys")
    public List<Survey> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

//    @RequestMapping("/surveys")
//    public String getAllSsurveys() {
//        return "hello";
//    }

    @RequestMapping("/surveys/{surveyId}")
    public Survey getSurveyById(@PathVariable String surveyId) {
        var survey = surveyService.getSurveyById(surveyId);

        if( survey==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return survey;
    }

    @RequestMapping("/surveys/{surveyId}/questions")
    public List<Question> getSurveyQuestions(@PathVariable String surveyId) {
        var questions = surveyService.getQuestionsBySurveyId(surveyId);

        if(questions==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return questions;
    }

    @RequestMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question getSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
        var questions = surveyService.getQuestionOfSurveyId(surveyId, questionId);

        if(questions==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return questions;
    }

    @RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question questions) {
        String questionId = surveyService.addNewSurveyQuestion(surveyId, questions);

        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId,
                                                       @PathVariable String questionId){
        surveyService.deleteSurveyQuestion(surveyId, questionId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId,
                                                       @PathVariable String questionId,
                                                       @RequestBody Question question){

        surveyService.updateSurveyQuestion(surveyId, questionId, question);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/langage", method = RequestMethod.GET)
    public String  internalization()
    {
        var locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage("good.morning.message",null, "Default Message", locale);
    }

}
