package com.myproject.board;

import com.myproject.board.question.QuestionEntity;
import com.myproject.board.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardApplicationTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 테스트_데이터_만들기(){
        QuestionEntity question = new QuestionEntity();
        question.setTitle("테스트 질문1");
        question.setContent("테스트 답변1");
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);

        QuestionEntity question2 = new QuestionEntity();
        question2.setTitle("테스트 질문2");
        question2.setContent("테스트 답변2");
        question2.setCreateDate(LocalDateTime.now());
        questionRepository.save(question2);
    }

    @Test
    void 데이터_조회(){
        List<QuestionEntity> all = questionRepository.findAll();
        assertEquals(4, all.size());

        QuestionEntity q = all.get(0);
        assertEquals("테스트 질문1", q.getTitle());
    }

    @Test
    void id로_찾기(){
        Optional<QuestionEntity> q = questionRepository.findById(1);
        if(q.isPresent()){
            QuestionEntity question = q.get();
            assertEquals("테스트 질문1", question.getTitle());
        }
    }

}