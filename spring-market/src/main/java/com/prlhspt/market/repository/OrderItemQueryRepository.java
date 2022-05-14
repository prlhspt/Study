package com.prlhspt.market.repository;

import com.prlhspt.market.config.dto.MemberSearchCondition;
import com.prlhspt.market.domain.Item.QItem;
import com.prlhspt.market.domain.Order;
import com.prlhspt.market.domain.OrderItem;
import com.prlhspt.market.domain.QOrderItem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.prlhspt.market.domain.Item.QItem.item;
import static com.prlhspt.market.domain.QMember.member;
import static com.prlhspt.market.domain.QOrder.order;
import static com.prlhspt.market.domain.QOrderItem.orderItem;

@RequiredArgsConstructor
@Repository
public class OrderItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<OrderItem> findAllByUsername(MemberSearchCondition condition) {
        return queryFactory
                .selectFrom(orderItem)
                .join(orderItem.order, order)
                .join(orderItem.item, item)
                .where(usernameEq(condition.getUsername()))
                .fetch();
    }

    public Long countAll(MemberSearchCondition condition) {
        return queryFactory
                .select(orderItem.count())
                .from(orderItem)
                .where(usernameEq(condition.getUsername()))
                .fetchOne();

    }

    private BooleanExpression usernameEq(String usernameCond) {

        return usernameCond != null ? orderItem.order.member.username.eq(usernameCond) : null;
    }

}
