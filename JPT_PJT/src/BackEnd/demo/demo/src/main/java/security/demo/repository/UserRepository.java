package security.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import security.demo.model.User;

import java.util.Optional;

// crud 함수를 jparepository가 들고있음
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    // SELECT * FROM user WHERE provider = ?1 and providerId = ?2
    Optional<User> findByProviderAndProviderId(String provider, String providerId);

}
