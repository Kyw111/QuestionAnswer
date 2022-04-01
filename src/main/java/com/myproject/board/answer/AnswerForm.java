package com.myproject.board.answer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AnswerForm {
    
    @NotEmpty(message = "내용 입력은 필수입니다.")
    private String content;
}
