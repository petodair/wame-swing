package br.com.wame.controller;

import br.com.wame.model.dto.ApiResponse;
import br.com.wame.service.AuthService;

public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    public ApiResponse<Void> autenticar(String username, String senha) {
        return authService.auth(username, senha);
    }
}
