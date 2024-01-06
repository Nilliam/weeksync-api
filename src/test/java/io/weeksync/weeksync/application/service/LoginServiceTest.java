package io.weeksync.weeksync.application.service;


import io.weeksync.weeksync.application.security.AccountUserDetails;
import io.weeksync.weeksync.domain.model.Account;
import io.weeksync.weeksync.presentation.dto.AccountInfo;
import io.weeksync.weeksync.presentation.dto.AuthenticationRequest;
import io.weeksync.weeksync.presentation.dto.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private AccountService accountService;

    @Test
    public void authenticateShouldReturnCorrectResponseWhenValidRequestIsGiven() {
        // Given
        String username = "testUser";
        ResponseCookie cookie = ResponseCookie.from("token", "testToken")
                .maxAge(JwtService.TOKEN_DURATION_SECONDS)
                .httpOnly(true)
                .sameSite(Cookie.SameSite.STRICT.attributeValue())
                .secure(true)
                .path("/")
                .build();

        AuthenticationRequest authenticationRequest = mock(AuthenticationRequest.class);
        Account account = new Account();
        account.setUsername(username);
        AccountInfo expectedAccountInfo = AccountInfo.builder()
                .name(username)
                .username(username)
                .build();
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .accountInfo(expectedAccountInfo)
                .cookie(cookie)
                .build();

        when(authenticationRequest.username()).thenReturn(username);
        when(accountService.loadUserByUsername(username)).thenReturn(new AccountUserDetails(account));
        when(authenticationService.authenticateLogin(authenticationManager, authenticationRequest)).thenReturn(cookie);

        // When
        AuthenticationResponse actualResponse = loginService.authenticate(authenticationRequest);

        // Then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void authenticateShouldThrowExceptionWhenInvalidRequestIsGiven() {
        // Given
        AuthenticationRequest authenticationRequest = mock(AuthenticationRequest.class);
        when(authenticationRequest.username()).thenReturn(null);

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loginService.authenticate(authenticationRequest);
        });

        // Then
        assertEquals("Username cannot be null", exception.getMessage());
    }

    @Test
    public void authenticateShouldThrowExceptionWhenUserDoesNotExist() {
        // Given
        String username = "nonExistingUser";
        AuthenticationRequest authenticationRequest = mock(AuthenticationRequest.class);
        when(authenticationRequest.username()).thenReturn(username);
        when(accountService.loadUserByUsername(username)).thenReturn(null);

        // When
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            loginService.authenticate(authenticationRequest);
        });

        // Then
        assertEquals("User not found", exception.getMessage());
    }
}