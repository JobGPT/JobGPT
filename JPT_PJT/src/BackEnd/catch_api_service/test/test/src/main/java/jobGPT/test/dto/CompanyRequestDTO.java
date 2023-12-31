package jobGPT.test.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
public class CompanyRequestDTO {
    private String compName;
    private String area;
    private String size;
    private String field;
    private String compinfo;
    private String industryName;
    @Builder.Default
    private List<String> recomendComps = new ArrayList<>();

}
