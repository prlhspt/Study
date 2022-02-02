package com.prlhspt.market.repository;

import com.prlhspt.market.domain.Item.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.prlhspt.market.domain.Item.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long countItem() {
        return queryFactory
                .select(item.count())
                .from(item)
                .fetchOne();
    }
}
