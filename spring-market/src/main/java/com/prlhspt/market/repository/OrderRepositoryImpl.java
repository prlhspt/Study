package com.prlhspt.market.repository;

import com.prlhspt.market.domain.Order;
import com.prlhspt.market.domain.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.prlhspt.market.domain.Item.QItem.item;
import static com.prlhspt.market.domain.QOrder.order;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findAllByFetchJoin() {
        return queryFactory
                .selectFrom(order)
                .fetch();
    }

    @Override
    public Long countAll() {
        return queryFactory
                .select(item.count())
                .from(item)
                .fetchOne();
    }
}
