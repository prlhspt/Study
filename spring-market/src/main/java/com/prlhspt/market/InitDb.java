package com.prlhspt.market;

import com.prlhspt.market.domain.*;
import com.prlhspt.market.domain.Item.Album;
import com.prlhspt.market.domain.Item.Book;
import com.prlhspt.market.domain.auth.Authority;
import com.prlhspt.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private final MemberService memberService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = Member.builder()
                    .username("member1")
                    .password("1234")
                    .authority(Authority.ROLE_USER)
                    .address(new Address("city1", "street1", "zipcode1"))
                    .build();

            em.persist(member);

            Album album1 = Album.builder()
                    .name("book1")
                    .price(10000)
                    .stockQuantity(10)
                    .artist("member1")
                    .etc("ETC1")
                    .build();

            Album album2 = Album.builder()
                    .name("book2")
                    .price(15000)
                    .stockQuantity(15)
                    .artist("member1")
                    .etc("ETC2")
                    .build();

            em.persist(album1);
            em.persist(album2);

            OrderItem orderItem1 = OrderItem.createOrderItem(album1, 10000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(album2, 20000, 4);

            Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);
        }

    }

}
