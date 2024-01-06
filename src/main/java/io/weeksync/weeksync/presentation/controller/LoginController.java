package io.weeksync.weeksync.presentation.controller;

import io.weeksync.weeksync.application.service.LoginService;
import io.weeksync.weeksync.presentation.dto.AccountInfo;
import io.weeksync.weeksync.presentation.dto.AuthenticationRequest;
import io.weeksync.weeksync.presentation.dto.AuthenticationResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = Objects.requireNonNull(loginService);
    }

    @PostMapping
    public ResponseEntity<AccountInfo> login(HttpServletResponse httpServletResponse, @RequestBody AuthenticationRequest authenticationRequest) {

        AuthenticationResponse authenticationResponse
                = loginService.authenticate(authenticationRequest);

        httpServletResponse.addHeader("Set-Cookie", authenticationResponse.cookie().toString());

        return ResponseEntity.ok(
                authenticationResponse.accountInfo()
        );
    }

}
