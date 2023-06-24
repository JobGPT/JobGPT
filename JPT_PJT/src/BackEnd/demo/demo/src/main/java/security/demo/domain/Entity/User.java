package security.demo.domain.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @CreationTimestamp
    private Timestamp createDate;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Chatbox> chatlist = new ArrayList<>();

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
    @Builder
    public User(int id, String username, String password, String email, String role, String provider, String providerId, String refreshToken, Timestamp createDate, List<Chatbox> chatlist) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.refreshToken = refreshToken;
        this.createDate = createDate;
        this.chatlist = chatlist;
    }
}