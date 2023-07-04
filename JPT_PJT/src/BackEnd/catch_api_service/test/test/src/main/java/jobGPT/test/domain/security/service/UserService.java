package jobGPT.test.domain.security.service;

import jobGPT.test.domain.security.DTO.SignUpDto;
import jobGPT.test.domain.security.Entity.User;
import jobGPT.test.domain.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void signUp(SignUpDto signUpDto) throws Exception{
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        User newuser = User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .username(signUpDto.getUsername())
                .role("ROLE_USER")
                .build();
        newuser.passwordEncode(passwordEncoder);
        userRepository.save(newuser);
    }

    public User search(String username) {
        User user = userRepository.findByUsername(username).get();
        return user;
    }
}
