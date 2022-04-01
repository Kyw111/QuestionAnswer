package com.myproject.board.SiteUser;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreateForm {

    @Size(max = 25)
    @NotEmpty(message = "사용자 id는 필수입니다")
    private String username;

    @NotEmpty(message = "비밀번호는 필수입니다")
    private String password1;

    @NotEmpty(message = "비밀번호를 확인해주세요")
    private String password2;

    @NotEmpty(message = "이메일을 입력하세요")
    @Email
    private String email;

}
