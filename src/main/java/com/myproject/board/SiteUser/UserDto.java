package com.myproject.board.SiteUser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UserDto {

    private Integer id;
    private String username;
    private String password;
    private String email;

}
