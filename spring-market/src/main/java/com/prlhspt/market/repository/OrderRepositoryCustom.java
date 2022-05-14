package com.prlhspt.market.repository;

import com.prlhspt.market.domain.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findAllByFetchJoin();

    Long countAll();
}
