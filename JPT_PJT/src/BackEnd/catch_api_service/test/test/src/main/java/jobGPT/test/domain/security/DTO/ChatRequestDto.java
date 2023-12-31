package jobGPT.test.domain.security.DTO;

import lombok.*;

import javax.persistence.Lob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRequestDto {

    @Lob
    private String talk;
    private Long talkboxId;
    private String title;
}
