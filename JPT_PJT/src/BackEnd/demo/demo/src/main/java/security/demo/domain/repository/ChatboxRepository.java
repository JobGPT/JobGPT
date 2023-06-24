package security.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.demo.domain.Entity.Chatbox;
import security.demo.domain.Entity.User;

import java.util.List;
import java.util.Optional;

public interface ChatboxRepository extends JpaRepository<Chatbox, Long> {
    Chatbox findByIdAndTitle(Long id, String title);
    List<Chatbox> findByUser(User user);
}
