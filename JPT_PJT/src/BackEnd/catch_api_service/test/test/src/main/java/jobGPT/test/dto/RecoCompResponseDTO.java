package jobGPT.test.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecoCompResponseDTO {
    private String title;
    @Builder.Default
    private List<RecoCompDTO> compName = new ArrayList<>();
}
