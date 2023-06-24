package security.demo.domain.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatBoxResponseDto {
    private Long id;
    private String username;
    private String title;
    @Builder.Default
    private List<ChatResponseDto> talklist = new ArrayList<>();
}
