package jobGPT.test.global.logout.service;

import jobGPT.test.domain.security.Entity.User;
import jobGPT.test.domain.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService {

    private final UserRepository userRepository;
    public void logoutByRefresh(String Token) {
        User user = userRepository.findByRefreshToken(Token).get();
        user.expireRefreshToken(LocalDateTime.now());
    }

    public void logoutByUsername(String username) {
        User user = userRepository.findByUsername(username).get();
        user.expireRefreshToken(LocalDateTime.now());
    }
}
