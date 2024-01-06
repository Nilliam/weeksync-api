package io.weeksync.weeksync.application.service;

import io.weeksync.weeksync.presentation.dto.AuthenticationRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

import static io.weeksync.weeksync.application.service.JwtService.TOKEN_DURATION_SECONDS;

@Service
public class AuthenticationService {
    private static final String COOKIE_NAME = "token";


    private JwtService jwtService;

    private UserDetailsService userDetailsService;

    public ResponseCookie authenticateLogin(
            AuthenticationManager authenticationManager,
            AuthenticationRequest authenticationRequest
    ) {

        final Authentication authentication = authenticate(authenticationManager, authenticationRequest);

        String token = jwtService.generateToken(authenticationRequest.username());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return buildCookie(token);
    }

    public void authenticateRequest(HttpServletRequest request) {
        String cookieToken = getCookieToken(request);
        String username = jwtService.extractUsername(cookieToken);

        if (username != null && jwtService.isTokenValid(cookieToken)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private Authentication authenticate(AuthenticationManager authenticationManager, AuthenticationRequest authenticationRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );
    }

    public ResponseCookie buildCookie(String token) {

        ResponseCookie.ResponseCookieBuilder responseCookieBuilder = ResponseCookie
                .from(COOKIE_NAME, token)
                .httpOnly(true)
                .maxAge(TOKEN_DURATION_SECONDS);

        responseCookieBuilder.sameSite(SameSite.STRICT.attributeValue());

        responseCookieBuilder.secure(true);

        return responseCookieBuilder.build();
    }

    private String getCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        return Arrays
                .stream(cookies)
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
