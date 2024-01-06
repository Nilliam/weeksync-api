package io.weeksync.weeksync.presentation.dto;

import lombok.Builder;
import org.springframework.http.ResponseCookie;

@Builder
public record AuthenticationResponse(ResponseCookie cookie, AccountInfo accountInfo) {}
