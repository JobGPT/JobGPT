package jobGPT.test.domain.security.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Chatbox {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="talkbox_id")
    private long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "chatbox", orphanRemoval = true)
    private List<Chat> talklist = new ArrayList<>();


    public void setUser(User user) {
        this.user = user;
        user.getChatlist().add(this);
    }
    @Builder
    public Chatbox(long id, String title, User user, List<Chat> talklist) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.talklist = talklist;
    }
}