package com.myproject.board.SiteUser;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column
    private String email;
}
