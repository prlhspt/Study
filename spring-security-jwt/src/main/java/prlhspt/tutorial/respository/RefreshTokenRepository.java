package prlhspt.tutorial.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import prlhspt.tutorial.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key);
}
