package com.myproject.board.answer;

import com.myproject.board.question.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Integer> {
}
