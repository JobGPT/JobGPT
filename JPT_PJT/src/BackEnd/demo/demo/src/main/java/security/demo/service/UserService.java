package security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.demo.model.User;
import security.demo.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final UserRepository userRepository;

    public void signUp(User user) throws Exception{
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        User newuser = new User();
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        newuser.setPassword(encPassword);
        newuser.setRole("ROLE_USER");
        newuser.setUsername(user.getUsername());
        userRepository.save(newuser);
    }
}
