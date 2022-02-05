package com.prlhspt.market.web.dto;

import com.prlhspt.market.domain.Item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ItemResponseDto {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    public ItemResponseDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }
}
