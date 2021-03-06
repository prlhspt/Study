package com.prlhspt.market.domain;

import com.prlhspt.market.domain.auth.Authority;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "password"})
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @Size(min = 3, max = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String username, String password, Authority authority, Address address) {
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.address = address;
    }

}
