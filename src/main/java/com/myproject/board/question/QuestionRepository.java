package com.myproject.board.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {

    QuestionEntity findByTitle(String title);
}
