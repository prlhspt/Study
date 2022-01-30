package com.prlhspt.market.Service;

import com.prlhspt.market.dto.MemberRequestDto;
import com.prlhspt.market.jwt.TokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TokenAuthenticationServiceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired AuthService authService;

    public static final String BEARER_PREFIX = "Bearer ";

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/member/me")).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {

        MemberRequestDto memberRequestDto = new MemberRequestDto("member", "qwer1234");
        authService.signup(memberRequestDto);

        String token = authService.login(memberRequestDto).getAccessToken();
        System.out.println("token = " + token);
        assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders
                .get("/member/me")
                .header("Authorization", BEARER_PREFIX + token))
                .andExpect(status().isOk());
    }

}