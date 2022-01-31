package com.prlhspt.market.Service;

import com.prlhspt.market.dto.MemberRequestDto;
import com.prlhspt.market.dto.auth.TokenDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired AuthService authService;

    private void createMember() {
        MemberRequestDto memberRequestDto = new MemberRequestDto("member", "qwer1234");
        authService.signup(memberRequestDto);
    }

    @DisplayName("아이디와 비밀번호가 일치했을 때 로그인이 성공해야 한다.")
    @Test
    public void successLogin() throws Exception{

        createMember();

        TokenDto tokenDto = authService.login(new MemberRequestDto("member", "qwer1234"));

        Assertions.assertThat(tokenDto.getAccessToken()).isNotNull();
        Assertions.assertThat(tokenDto.getRefreshToken()).isNotNull();
        Assertions.assertThat(tokenDto.getAccessTokenExpiresIn()).isNotNull();
        Assertions.assertThat(tokenDto.getGrantType()).isNotNull();

    }

    @DisplayName("아이디와 비밀번호가 다르면 로그인이 실패해야 한다.")
    @Test
    public void failLogin() throws Exception{

        createMember();

        BadCredentialsException thrown = assertThrows(BadCredentialsException.class, () ->
                authService.login(new MemberRequestDto("member", "error")));
        assertEquals("Bad credentials", thrown.getMessage());

    }

}