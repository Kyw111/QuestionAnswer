package com.myproject.board;

import com.myproject.board.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Test
	void 테스트데이터_만들기() {
		for(int i = 1; i <= 50; i++){
			String testTitle = String.format("질문 있습니다%d", i);
			String testContent = String.format("질문 내용%d", i);
			questionService.createQuestion(testTitle, testContent, null);
		}

	}

}
