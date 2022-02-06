package com.prlhspt.market.service;

import com.prlhspt.market.domain.Item.Album;
import com.prlhspt.market.domain.Order;
import com.prlhspt.market.domain.OrderStatus;
import com.prlhspt.market.jwt.dto.TokenDto;
import com.prlhspt.market.web.dto.LoginRequestDto;
import com.prlhspt.market.web.dto.MemberResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired AuthService authService;
    @Autowired OrderService orderService;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;

    @Test
    @DisplayName("주문 저장 성공")
    public void save() throws Throwable {
        //given
        Album album = (Album) itemService.saveItem(new Album("album1", 1000, 2, "artist1", "etc1"));

        MemberResponseDto memberResponseDto = authService.signup(new LoginRequestDto("member", "qwer1234"));

        Long orderId = orderService.order(memberResponseDto.getId(), album.getId(), 1);

        //when
        List<Order> findOrders = orderService.findOrders();

        //then
        assertThat(findOrders).extracting("id").containsExactly(orderId);

    }

    @Test
    @DisplayName("주문 취소 성공")
    @Rollback(value = false)
    public void cancel() throws Throwable {
        //given
        Album album = (Album) itemService.saveItem(new Album("album1", 1000, 2, "artist1", "etc1"));

        MemberResponseDto memberResponseDto = authService.signup(new LoginRequestDto("member", "qwer1234"));

        Long orderId = orderService.order(memberResponseDto.getId(), album.getId(), 1);

        //when
        orderService.cancelOrder(orderId);

        List<Order> findOrders = orderService.findOrders();

        //then
        assertThat(findOrders).extracting("status").containsExactly(OrderStatus.CANCEL);

    }



}