package com.myproject.board.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {

    QuestionEntity findByTitle(String title);
    Page<QuestionEntity> findAll(Pageable pageable);
}
