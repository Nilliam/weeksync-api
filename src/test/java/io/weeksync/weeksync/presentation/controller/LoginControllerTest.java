package io.weeksync.weeksync.presentation.controller;

import io.weeksync.weeksync.presentation.dto.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginShouldReturnAccountInfoWhenValidRequestIsGiven() throws Exception {
        // Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("testUser", "testPassword");

        // When
        MvcResult result = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\", \"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String response = result.getResponse().getContentAsString();
        assertEquals("{\"username\":\"testUser\", \"email\":\"testUser\"}", response);
    }

    @Test
    public void loginShouldSetCookieWhenValidRequestIsGiven() throws Exception {
        // Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("testUser", "testPassword");

        // When
        MvcResult result = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\", \"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String cookie = result.getResponse().getHeader("Set-Cookie");
        assertEquals("cookieValue", cookie);
    }
}