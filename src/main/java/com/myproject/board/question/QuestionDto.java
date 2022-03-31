package com.myproject.board.question;

import com.myproject.board.answer.AnswerDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionDto {

    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private List<AnswerDto> answerList;
}
