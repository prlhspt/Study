package com.prlhspt.market.entity.domain.Item;

import com.prlhspt.market.entity.domain.BaseEntity;
import com.prlhspt.market.entity.domain.ItemCategory;
import com.prlhspt.market.exception.NotEnoughStockException;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.InheritanceType.JOINED;

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Item extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<ItemCategory> itemCategories = new ArrayList<ItemCategory>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (stockQuantity < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stockQuantity = restStock;

    }

}
