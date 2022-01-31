package com.prlhspt.market.repository;

import com.prlhspt.market.entity.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
