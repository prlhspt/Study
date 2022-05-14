package com.prlhspt.market.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prlhspt.market.domain.Item.Album;
import com.prlhspt.market.domain.Item.Item;
import com.prlhspt.market.jwt.dto.TokenDto;
import com.prlhspt.market.repository.OrderItemRepository;
import com.prlhspt.market.service.AuthService;
import com.prlhspt.market.service.ItemService;
import com.prlhspt.market.service.MemberService;
import com.prlhspt.market.service.OrderService;
import com.prlhspt.market.web.dto.LoginRequestDto;
import com.prlhspt.market.web.dto.MemberResponseDto;
import com.prlhspt.market.web.dto.OrderRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired MockMvc mvc;

    @Autowired AuthService authService;
    @Autowired OrderService orderService;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;
    @Autowired OrderItemRepository orderItemRepository;


    @Autowired ObjectMapper objectMapper;

    public static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("해당 유저에 대한 전체 주문 리스트 출력")
    void orderList() {



    }

    @Test
    @DisplayName("로그인 하지 않고 주문을 시도하면 권한 에러를 발생시킨다.")
    void failedSave() throws Exception {

        LoginRequestDto loginRequestDto = new LoginRequestDto("member", "qwer1234");
        MemberResponseDto memberResponseDto = authService.signup(loginRequestDto);

        authService.login(loginRequestDto);

        Album album = (Album) itemService.saveItem(new Album("album1", 1000, 2, "artist1", "etc1"));

        String content = objectMapper.writeValueAsString(new OrderRequestDto(memberResponseDto.getId(), album.getId(), 1));

        mvc.perform(MockMvcRequestBuilders
                        .post("/orders/save")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인 후 주문을 시도하면 테스트가 성공한다.")
    void successSave() throws Exception {

        LoginRequestDto loginRequestDto = new LoginRequestDto("member", "qwer1234");
        MemberResponseDto memberResponseDto = authService.signup(loginRequestDto);

        TokenDto tokenDto = authService.login(loginRequestDto);

        Album album = (Album) itemService.saveItem(new Album("album1", 1000, 2, "artist1", "etc1"));

        String content = objectMapper.writeValueAsString(new OrderRequestDto(memberResponseDto.getId(), album.getId(), 1));

        System.out.println("content = " + content);

        mvc.perform(MockMvcRequestBuilders
                        .post("/orders/save")
                        .header("Authorization", BEARER_PREFIX + tokenDto.getAccessToken())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}