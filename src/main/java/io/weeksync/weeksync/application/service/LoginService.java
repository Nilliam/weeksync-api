package io.weeksync.weeksync.application.service;

import io.weeksync.weeksync.domain.model.Account;
import io.weeksync.weeksync.presentation.dto.AccountInfo;
import io.weeksync.weeksync.presentation.dto.AuthenticationRequest;
import io.weeksync.weeksync.presentation.dto.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountService accountService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var cookie = authenticationService.authenticateLogin(authenticationManager, authenticationRequest);
        var account = (Account) accountService.loadUserByUsername(authenticationRequest.username());

        var accountInfo = AccountInfo.builder()
                .name(account.getUsername())
                .username(account.getUsername())
                .build();

        return AuthenticationResponse.builder()
                .accountInfo(accountInfo)
                .cookie(cookie)
                .build();
    }

}
