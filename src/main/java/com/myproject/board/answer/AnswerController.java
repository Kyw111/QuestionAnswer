package com.myproject.board.answer;

import com.myproject.board.question.QuestionDto;
import com.myproject.board.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String create(Model model, @PathVariable Integer id,
                         @Valid AnswerForm answerForm, BindingResult bindingResult) {
        QuestionDto questionDto = questionService.getQuestion(id); // 해당질문에 대한 답변 생성이니까 해당 질문 id값을 먼저 불러옴
        if(bindingResult.hasErrors()){
            model.addAttribute("question", questionDto);
            return "/question_detail";
        }
        answerService.createAnswer(questionDto, answerForm.getContent());
        return "redirect:/question/detail/{id}";
    }
}
