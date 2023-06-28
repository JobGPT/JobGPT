package jobGPT.test.domain.security.repository;


import jobGPT.test.domain.security.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// crud 함수를 jparepository가 들고있음
public interface  UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String findByEmail);

    // SELECT * FROM user WHERE provider = ?1 and providerId = ?2
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
    Optional<User> findByRefreshToken(String refreshToken);


}
