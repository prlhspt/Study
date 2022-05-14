package com.prlhspt.market.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prlhspt.market.domain.Item.Item;
import com.prlhspt.market.domain.Member;
import com.prlhspt.market.domain.auth.Authority;
import com.prlhspt.market.service.AuthService;
import com.prlhspt.market.service.ItemService;
import com.prlhspt.market.web.dto.AlbumRequestDto;
import com.prlhspt.market.web.dto.LoginRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class SpringSecurityAuditorAwareTest {

    @PersistenceContext EntityManager em;

    @Autowired MockMvc mvc;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired ObjectMapper objectMapper;
    @Autowired AuthService authService;
    @Autowired ItemService itemService;

    public static final String BEARER_PREFIX = "Bearer ";

    private void createMember() {

        Member admin = Member.builder()
                .username("admin")
                .authority(Authority.ROLE_ADMIN)
                .password(passwordEncoder.encode("admin"))
                .build();
        em.persist(admin);

        Member member = Member.builder()
                .username("member")
                .authority(Authority.ROLE_USER)
                .password(passwordEncoder.encode("member"))
                .build();
        em.persist(member);

        Member guest = Member.builder()
                .username("guest")
                .authority(Authority.ROLE_GUEST)
                .password(passwordEncoder.encode("guest"))
                .build();
        em.persist(guest);

    }

    @Test
    @DisplayName("Role이 Admin일 경우 CreateBy 에 닉네임이 남아야 한다")
    void createByAdminTest() throws Throwable {
        createMember();

        String token = authService.login(new LoginRequestDto("admin", "admin")).getAccessToken();
        assertNotNull(token);

        AlbumRequestDto albumRequestDto = new AlbumRequestDto("item1", 10000, 100, "artist1", "etc1");

        String content = objectMapper.writeValueAsString(albumRequestDto);

        mvc.perform(MockMvcRequestBuilders
                        .post("/items/save/album")
                        .header("Authorization", BEARER_PREFIX + token)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Item findItem = itemService.findByName("item1");
        String createdBy = findItem.getCreatedBy();

        Assertions.assertThat(createdBy).isEqualTo("admin");

    }

    @Test
    @DisplayName("Role이 User일 경우 CreateBy 에 닉네임이 남아야 한다")
    void createByUserTest() throws Throwable {
        createMember();

        String token = authService.login(new LoginRequestDto("member", "member")).getAccessToken();
        assertNotNull(token);

        AlbumRequestDto albumRequestDto = new AlbumRequestDto("item1", 10000, 100, "artist1", "etc1");

        String content = objectMapper.writeValueAsString(albumRequestDto);

        mvc.perform(MockMvcRequestBuilders
                        .post("/items/save/album")
                        .header("Authorization", BEARER_PREFIX + token)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("test"));

        Item findItem = itemService.findByName("item1");
        String createdBy = findItem.getCreatedBy();

        Assertions.assertThat(createdBy).isEqualTo("member");

    }

    @Test
    @DisplayName("Role이 GUEST일 경우 CreateBy 에 닉네임이 남지 말아야 한다")
    void createByGuestTest() throws Throwable {
        createMember();

        String token = authService.login(new LoginRequestDto("guest", "guest")).getAccessToken();
        assertNotNull(token);

        AlbumRequestDto albumRequestDto = new AlbumRequestDto("item1", 10000, 100, "artist1", "etc1");

        String content = objectMapper.writeValueAsString(albumRequestDto);

        mvc.perform(MockMvcRequestBuilders
                        .post("/items/save/album")
                        .header("Authorization", BEARER_PREFIX + token)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Item findItem = itemService.findByName("item1");
        String createdBy = findItem.getCreatedBy();

        Assertions.assertThat(createdBy).isNull();

    }

}