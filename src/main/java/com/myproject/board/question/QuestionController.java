package com.myproject.board.question;

import com.myproject.board.SiteUser.UserDto;
import com.myproject.board.SiteUser.UserService;
import com.myproject.board.answer.AnswerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm) {
        return "question_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create") // bindingResult는 @Valid로 questionForm의 title,content를 검증수행된 결과를 의미하는 객체
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){

        if (bindingResult.hasErrors()){ // 검증완료된 questionForm 데이터에 에러가 있다면
            return "question_create";
        }
        UserDto userDto = userService.getUser(principal.getName());
        questionService.createQuestion(questionForm.getTitle(), questionForm.getContent(), userDto);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        QuestionDto questionDto = this.questionService.getQuestion(id);
        if(!questionDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setTitle(questionDto.getTitle());
        questionForm.setContent(questionDto.getContent());
        return "question_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@Valid QuestionForm questionForm, @PathVariable("id") Integer id,
                         BindingResult bindingResult,
                         Principal principal){
        if (bindingResult.hasErrors()) {
            return "question_create";
        }

        QuestionDto questionDto = questionService.getQuestion(id);
        if (!questionDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modifyQuestion(questionForm.getTitle(), questionForm.getContent(), questionDto);
        return "redirect:/question/detail/{id}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Integer id, Principal principal) {
        QuestionDto questionDto = questionService.getQuestion(id);
        if (!questionDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        questionService.deleteQuestion(questionDto);
        return "redirect:/";
    }


}
