package security.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.demo.domain.Entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
