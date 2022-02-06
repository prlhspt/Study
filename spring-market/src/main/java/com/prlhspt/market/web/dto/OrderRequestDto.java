package com.prlhspt.market.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull
    private Long memberId;

    @NotNull
    private Long itemId;

    @NotNull
    private int count;

    public OrderRequestDto(Long memberId, Long itemId, int count) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.count = count;
    }
}
