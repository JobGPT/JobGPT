package security.demo.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.demo.domain.DTO.SignUpDto;
import security.demo.domain.Entity.User;
import security.demo.domain.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final UserRepository userRepository;

    public void signUp(SignUpDto signUpDto) throws Exception{
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        User newuser = new User();
        String rawPassword = signUpDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        newuser.setPassword(encPassword);
        newuser.setRole("ROLE_USER");
        newuser.setUsername(signUpDto.getUsername());
        newuser.setEmail(signUpDto.getEmail());
        userRepository.save(newuser);
    }

    public User search(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
