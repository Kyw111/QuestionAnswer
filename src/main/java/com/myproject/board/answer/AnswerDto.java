package com.myproject.board.answer;

import com.myproject.board.question.QuestionDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerDto {

    private Integer id;
    private String content;
    private LocalDateTime createDate;
    private QuestionDto question;
}
