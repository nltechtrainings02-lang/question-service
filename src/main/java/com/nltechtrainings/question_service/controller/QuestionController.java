package com.nltechtrainings.question_service.controller;

import com.nltechtrainings.question_service.dto.QuestionDto;
import com.nltechtrainings.question_service.dto.QuestionResponseDto;
import com.nltechtrainings.question_service.model.Question;
import com.nltechtrainings.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        System.out.println("Getting all questions from the service");
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category.toUpperCase());
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return  questionService.addQuestion(question);
    }

    // generate questions - list of question ids
    // get all quiz questions based on the question ids
    // calculate score

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuizQuestions(@RequestParam String categoryName, @RequestParam Integer numQ){
        return questionService.getQuizQuestions(categoryName, numQ);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionDto>> getQuestionsFromIds(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromIds(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuestionResponseDto> questionResponseDtos){
        return questionService.getScore(questionResponseDtos);
    }

}
