package com.nltechtrainings.question_service.service;

import com.nltechtrainings.question_service.dao.QuestionDao;
import com.nltechtrainings.question_service.dto.QuestionDto;
import com.nltechtrainings.question_service.dto.QuestionResponseDto;
import com.nltechtrainings.question_service.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    private static Logger log = LoggerFactory.getLogger(QuestionService.class);

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            log.error("Could not find questions: ", e);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            log.error("Could not find questions by category: ", e);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
        } catch (Exception e){
            log.error("Could not save new question: ", e);
        }
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuizQuestions(String categoryName, Integer numQ) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numQ);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionDto>> getQuestionsFromIds(List<Integer> questionIds) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id: questionIds){
            questions.add(questionDao.findById(id).get());
        }

        for(Question question: questions){
            QuestionDto questionDto = new QuestionDto();
            questionDto.setQuestionTitle(question.getQuestionTitle());
            questionDto.setId(question.getId());
            questionDto.setOption1(question.getOption1());
            questionDto.setOption2(question.getOption2());
            questionDto.setOption3(question.getOption3());
            questionDto.setOption4(question.getOption4());
            questionDtos.add(questionDto);
        }

        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<QuestionResponseDto> questionResponseDtos) {
        int score = 0;
        for(int i=0; i<questionResponseDtos.size(); i++){
            QuestionResponseDto questionResponse = questionResponseDtos.get(i);
            Question quizQuestion = questionDao.findById(questionResponse.getId()).get();
            if(questionResponse.getResponse().equals(quizQuestion.getRightAnswer())) {
                score++;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
