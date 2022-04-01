package com.myproject.board.SiteUser;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ROLE_ADMIN"),
    USER("USER_ROLE");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

}
