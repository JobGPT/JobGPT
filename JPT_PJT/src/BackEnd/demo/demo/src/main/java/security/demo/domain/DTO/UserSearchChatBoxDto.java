package security.demo.domain.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchChatBoxDto {
    private String username;
    @Builder.Default
    private List<ChatBoxResponseDto> chatbox = new ArrayList<>();
}
