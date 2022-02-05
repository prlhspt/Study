package com.prlhspt.market.repository;

import com.prlhspt.market.domain.Item.Item;
import com.prlhspt.market.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long>, ItemRepositoryCustom {

    Optional<Item> findByName(String name);

}
