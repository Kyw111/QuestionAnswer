package com.myproject.board.answer;

import com.myproject.board.SiteUser.UserDto;
import com.myproject.board.SiteUser.UserService;
import com.myproject.board.question.QuestionDto;
import com.myproject.board.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(Model model, @PathVariable Integer id,
                         @Valid AnswerForm answerForm, BindingResult bindingResult,
                         Principal principal) {
        QuestionDto questionDto = questionService.getQuestion(id); // 해당질문에 대한 답변 생성이니까 해당 질문 id값을 먼저 불러옴
        UserDto userDto = userService.getUser(principal.getName()); // principal 객체를 통해 사용자명을 얻은 다음, 그사용자명으로 userDto객체를 얻고 AnswerService.create메소드에 전달
        if(bindingResult.hasErrors()){
            model.addAttribute("question", questionDto);
            return "/question_detail";
        }
        answerService.createAnswer(questionDto, answerForm.getContent(), userDto);
        return "redirect:/question/detail/{id}";
    }
}
