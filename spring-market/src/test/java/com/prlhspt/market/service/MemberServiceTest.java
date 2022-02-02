package com.prlhspt.market.service;

import com.prlhspt.market.web.dto.MemberRequestDto;
import com.prlhspt.market.domain.auth.Authority;
import com.prlhspt.market.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberServiceTest {

    @PersistenceContext EntityManager em;

    @Autowired MockMvc mvc;
    @Autowired PasswordEncoder passwordEncoder;

    @Autowired AuthService authService;



    public static final String BEARER_PREFIX = "Bearer ";

    private void createMember() {

        Member admin = new Member("admin", passwordEncoder.encode("admin"), Authority.ROLE_ADMIN);
        em.persist(admin);

        Member testMember = new Member("member", passwordEncoder.encode("qwer1234"), Authority.ROLE_USER);
        em.persist(testMember);

    }

    @Test
    @DisplayName("허가되지 않은 권한의 기능은 접근이 금지되어야 한다.")
    void shouldNotAllowAccessNoAuthorization() throws Exception {
        createMember();

        String token = authService.login(new MemberRequestDto("member", "qwer1234")).getAccessToken();
        assertNotNull(token);

        mvc.perform(MockMvcRequestBuilders
                        .get("/member/member")
                        .header("Authorization", BEARER_PREFIX + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("허가된 권한의 기능은 접근이 가능해야 한다.")
    void shouldAllowAccessNoAuthorization() throws Exception {
        createMember();

        String token = authService.login(new MemberRequestDto("admin", "admin")).getAccessToken();
        assertNotNull(token);

        mvc.perform(MockMvcRequestBuilders
                        .get("/member/member")
                        .header("Authorization", BEARER_PREFIX + token))
                .andExpect(status().isOk());
    }

}