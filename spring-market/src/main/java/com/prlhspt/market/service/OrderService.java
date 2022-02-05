package com.prlhspt.market.service;

import com.prlhspt.market.domain.*;
import com.prlhspt.market.domain.Item.Item;
import com.prlhspt.market.repository.ItemRepository;
import com.prlhspt.market.repository.MemberRepository;
import com.prlhspt.market.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) throws Throwable {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다"));

        Item item = (Item) itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .status(DeliveryStatus.READY)
                .build();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();

    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 정보가 없습니다"));

        order.cancel();
    }

}
