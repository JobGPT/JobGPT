package security.demo.domain.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Chat {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="talk_id")
    private long id;

    @Column(nullable = false)
    private String writer;

    @Lob
    @Column(nullable = false)
    private String talk;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chatbox_id",nullable = false)
    private Chatbox chatbox;

    public void setChatbox(Chatbox chatbox) {
        this.chatbox = chatbox;
        chatbox.getTalklist().add(this);
    }
    @Builder
    public Chat(long id, String writer, String talk, Chatbox chatbox) {
        this.id = id;
        this.writer = writer;
        this.talk = talk;
        this.chatbox = chatbox;
    }
}