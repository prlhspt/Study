package com.prlhspt.market.entity.domain;

import com.prlhspt.market.entity.auth.Authority;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "password"})
public class Member {

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

    @Builder
    public Member(String username, String password, Authority authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public Member(String username) {
        this.username = username;
    }



}
