package com.prlhspt.market.web.dto;

import com.prlhspt.market.domain.Order;
import com.prlhspt.market.domain.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderResponseDto {

    private Long memberId;
    private Long orderId;
    private String username;
    private String itemName;

    public OrderResponseDto(OrderItem orderItem) {
        this.memberId = orderItem.getOrder().getMember().getId();
        this.orderId = orderItem.getOrder().getId();
        this.username = orderItem.getOrder().getMember().getUsername();
        this.itemName = orderItem.getItem().getName();
    }
}
