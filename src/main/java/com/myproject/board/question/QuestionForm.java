package com.myproject.board.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionForm { // 화면에서 전달되는 입력 값을 검증하기 위한 클래스 - 글 작성 시 내용없는 게시글 작성 방지를 위해서

    @Size(max = 200)
    @NotEmpty(message = "제목을 입력하세요")
    private String title;

    @NotEmpty(message = "내용을 입력하세요")
    private String content;

}
