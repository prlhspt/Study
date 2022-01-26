package prlhspt.tutorial.respository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import prlhspt.tutorial.entity.Authority;
import prlhspt.tutorial.entity.User;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저가 DB에 저장이 잘 되는지 확인")
    void savedUser() {

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username("user1")
                .password("1234")
                .nickname("coffee")
                .activated(true)
                .authorities(Collections.singleton(authority))
                .build();

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isEqualTo(user);
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());


    }

    @Test
    @DisplayName("유저가 Authorities 포함해서 잘 검색 되는지 확인")
    void findOneWithAuthoritiesByUsername() {

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username("user1")
                .password("1234")
                .nickname("coffee")
                .activated(true)
                .authorities(Collections.singleton(authority))
                .build();

        User savedUser = userRepository.save(user);

        User findUser = userRepository.findOneWithAuthoritiesByUsername(savedUser.getUsername()).get();

        assertThat(findUser).isEqualTo(savedUser);
        assertThat(findUser.getUsername()).isEqualTo(savedUser.getUsername());
        assertThat(findUser.getAuthorities()).isEqualTo(savedUser.getAuthorities());

    }
}