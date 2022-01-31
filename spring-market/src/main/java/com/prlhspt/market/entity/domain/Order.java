package com.prlhspt.market.entity.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    private Order(Member member, Delivery delivery, LocalDateTime orderDate,
                  OrderStatus status,  OrderItem... orderItems) {
        this.member = member;
        this.delivery = delivery;
        this.orderDate = orderDate;
        this.status = status;

        member.getOrders().add(this);
        delivery.setOrder(this);

    }

    private void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }

    private void changeStatus(OrderStatus status) {
        this.status = status;
    }

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.ORDER)
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        return order;
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalArgumentException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.changeStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;

    }

}
