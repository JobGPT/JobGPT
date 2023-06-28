package jobGPT.test.domain.security.repository;

import jobGPT.test.domain.security.Entity.Chatbox;
import jobGPT.test.domain.security.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatboxRepository extends JpaRepository<Chatbox, Long> {
    Chatbox findByIdAndTitle(Long id, String title);
    List<Chatbox> findByUser(User user);

    void deleteById(Long id);
}
