package com.nltechtrainings.question_service;

import com.nltechtrainings.question_service.controller.QuestionController;
import com.nltechtrainings.question_service.model.Question;
import com.nltechtrainings.question_service.service.QuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class QuestionServiceApplicationTests {

	//@Spy
	@Mock
	private QuestionService questionService;

	@Captor
	private ArgumentCaptor<String> categoryCaptor;

	@InjectMocks
	private QuestionController questionController;

//	@BeforeEach
//	void setUp(){
//		MockitoAnnotations.openMocks(this);
//	}

	@Test
	void contextLoads() {
	}

	@Test
	void getAllQuestions_returnsEmptyList(){
		Mockito.when(questionService.getAllQuestions()).thenReturn(new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));
		ResponseEntity<List<Question>> questions = questionController.getAllQuestions();
		Assertions.assertTrue(questions.getBody().isEmpty());
		Assertions.assertEquals(HttpStatusCode.valueOf(200), questions.getStatusCode());
		Mockito.verify(questionService, Mockito.times(1)).getAllQuestions();
	}

	@Test
	void getQuestionsByCategory_onPassingCategory_returnsExpectedOutput(){
		Mockito.when(questionService.getQuestionsByCategory(Mockito.any())).thenReturn(new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));
		ResponseEntity<List<Question>> questions = questionController.getQuestionsByCategory("Java");
		Mockito.verify(questionService).getQuestionsByCategory(categoryCaptor.capture());
		Assertions.assertEquals("JAVA", categoryCaptor.getValue());
	}

}
