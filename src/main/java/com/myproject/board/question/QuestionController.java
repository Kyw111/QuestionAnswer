package com.myproject.board.question;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @RequestMapping("/list")
    public String list(Model model) {
        List<QuestionDto> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        QuestionDto question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String create() {
        return "question_create";
    }

    @PostMapping("/create")
    public String create(@RequestParam String title, @RequestParam String content){
        questionService.createQuestion(title, content);
        return "redirect:/question/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(@RequestParam String title, @RequestParam String content, @PathVariable Integer id) {
        QuestionDto questionDto = questionService.getQuestion(id);
        questionService.modifyQuestion(title, content, id);
        return "question_form";
    }
}
