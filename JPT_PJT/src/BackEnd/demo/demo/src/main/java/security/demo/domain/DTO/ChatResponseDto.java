package security.demo.domain.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponseDto {
    private String writer;
    private String talk;

}
