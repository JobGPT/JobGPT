package security.demo.domain.DTO;

import lombok.*;

import javax.persistence.Lob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRequestDto {
    private String username;
    @Lob
    private String talk;
    private Long talkboxId;
    private String title;
}
