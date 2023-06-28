package jobGPT.test.domain.security.repository;

import jobGPT.test.domain.security.Entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, Long> {
}
