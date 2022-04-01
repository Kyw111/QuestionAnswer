package com.myproject.board.SiteUser;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController { // 유저 회원가입을 위한 컨트롤러

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "PasswordDifferentEachOther", "두 비밀번호가 일치하지 않습니다.");
            return "signup"; // 리턴을 해줘야만 오류메세지가 출력됨 - 실수로 return을 빼먹었더니 오류가 뜨지않았음.
        }
        try {
            userService.CreateUser(userCreateForm.getUsername(), userCreateForm.getPassword1(), userCreateForm.getEmail());
        } catch (DataIntegrityViolationException e) { // unique로 지정해둔 유저ID(UserCreateForm.username)가 회원가입시 중복됐을때 발생하는 예외임
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다");
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    @PostMapping("/login")
//    public String login(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
//
//
//
//        return "redirec:/";
//    }
}
