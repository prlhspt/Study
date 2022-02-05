package com.prlhspt.market.service;

import com.prlhspt.market.domain.Item.Item;
import com.prlhspt.market.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item saveItem(Item item) {
        return (Item) itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findByName(String name) throws Throwable {
        return (Item) itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다"));
    }

    public Item findOne(Long itemId) throws Throwable {
        return (Item) itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품 수량이 부족합니다"));
    }

    public Long countItem() {
        return itemRepository.countItem();
    }
}
