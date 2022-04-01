package com.myproject.board.question;

import com.myproject.board.answer.AnswerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) { // 페이징처리를 위해 page를 파라미터로 넣어줌
        Page<QuestionDto> paging = questionService.getList(page); // 페이징처리를 위해 변수타입 수정해줌
        model.addAttribute("paging", paging); // model명을 템플릿에서도 바꿔줘야함
        return "question_list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model, AnswerForm answerForm) {
        QuestionDto question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String create(QuestionForm questionForm) {
        return "question_create";
    }

    @PostMapping("/create") // bindingResult는 @Valid로 questionForm의 title,content를 검증수행된 결과를 의미하는 객체
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()){ // 검증완료된 questionForm 데이터에 에러가 있다면
            return "question_create";
        }
        questionService.createQuestion(questionForm.getTitle(), questionForm.getTitle());
        return "redirect:/question/list";
    }

}
