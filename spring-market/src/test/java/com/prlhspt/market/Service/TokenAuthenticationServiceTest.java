package com.prlhspt.market.Service;

import com.prlhspt.market.dto.MemberRequestDto;
import com.prlhspt.market.jwt.TokenProvider;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("토큰 없이 API 요청 시 로그인이 거부되어야 한다.")
    public void shouldNotAllowAccessNoToken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/member/me")).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("잘못된 토큰으로 API 요청 시 로그인이 거부되어야 한다.")
    public void shouldNotAllowAccessWrongToken() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/member/me")
                        .header("Authorization", BEARER_PREFIX + "test1234"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인 후 발급된 토큰으로 API 요청 시 정상 작동하여야 한다.")
    public void shouldGenerateAuthToken() throws Exception {

        MemberRequestDto memberRequestDto = new MemberRequestDto("member", "qwer1234");
        authService.signup(memberRequestDto);

        String token = authService.login(memberRequestDto).getAccessToken();
        assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders
                .get("/member/me")
                .header("Authorization", BEARER_PREFIX + token))
                .andExpect(status().isOk());
    }

}