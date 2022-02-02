package com.prlhspt.market.service;

import com.prlhspt.market.domain.Address;
import com.prlhspt.market.domain.Member;
import com.prlhspt.market.exception.DuplicateMemberException;
import com.prlhspt.market.repository.MemberRepository;
import com.prlhspt.market.web.dto.LoginRequestDto;
import com.prlhspt.market.jwt.dto.TokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired PasswordEncoder passwordEncoder;
    @Autowired MemberRepository memberRepository;
    @Autowired AuthService authService;

    private LoginRequestDto createMember() {
        return new LoginRequestDto("member", "qwer1234");
    }

    @Test
    @DisplayName("회원가입 성공")
    void successSignup() {

        LoginRequestDto loginRequestDto = createMember();
        authService.signup(loginRequestDto);

        Member findMember = memberRepository.findByUsername("member").orElse(null);
        assertThat(findMember.getUsername()).isEqualTo("member");
    }

    @Test
    @DisplayName("회원가입 할 때 같은 이름이 있으면 예외가 발생해야 한다")
    void duplicatedLogin(){

        LoginRequestDto loginRequestDto = createMember();
        authService.signup(loginRequestDto);

        assertThatThrownBy(() -> authService.signup(new LoginRequestDto("member", "error")))
                .isInstanceOf(DuplicateMemberException.class)
                .hasMessageContaining("이미 가입되어 있는 유저입니다");
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치했을 때 로그인이 성공해야 한다")
    void successLogin(){

        LoginRequestDto loginRequestDto = createMember();
        authService.signup(loginRequestDto);

        TokenDto tokenDto = authService.login(new LoginRequestDto("member", "qwer1234"));

        assertThat(tokenDto.getAccessToken()).isNotNull();
        assertThat(tokenDto.getRefreshToken()).isNotNull();
        assertThat(tokenDto.getAccessTokenExpiresIn()).isNotNull();
        assertThat(tokenDto.getGrantType()).isNotNull();

    }

    @Test
    @DisplayName("아이디와 비밀번호가 다르면 로그인이 실패해야 한다")
    void failLogin(){

        LoginRequestDto loginRequestDto = createMember();
        authService.signup(loginRequestDto);

        assertThatThrownBy(() -> authService.login(new LoginRequestDto("member", "error")))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Bad credentials");
    }

}