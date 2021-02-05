package com.example.demo.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthForm {

    @NotNull(message = "Логин не должен быть пустым")
    @NotEmpty(message = "Логин не должен быть пустым")
    private String login;

    @NotNull(message = "Пароль не должен быть пустым")
    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

}
