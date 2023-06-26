package security.demo.domain.Entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;
import security.demo.global.jwt.util.DateTimeUtils;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;
    private String username;
    private String password;
    private String email;
    private String role; //ROLE_USER, ROLE_ADMIN
    // OAuth를 위해 구성한 추가 필드 2개
    private String provider;
    private String providerId;
    private String refreshToken;
    private LocalDateTime tokenExpirationTime;
    @CreationTimestamp
    private Timestamp createDate;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Chatbox> chatlist = new ArrayList<>();

    public void updateRefreshToken(String updateRefreshToken, Date tokenExpirationTime) {
        this.refreshToken = updateRefreshToken;
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(tokenExpirationTime);
    }
    @Builder
    public User(int id, String username, String password, String email, String role, String provider, String providerId, String refreshToken, LocalDateTime tokenExpirationTime ,Timestamp createDate, List<Chatbox> chatlist) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.refreshToken = refreshToken;
        this.tokenExpirationTime = tokenExpirationTime;
        this.createDate = createDate;
        this.chatlist = chatlist;
    }
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }
}