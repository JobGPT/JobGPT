package jobGPT.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Builder
@Setter @Getter
@AllArgsConstructor
public class CompanyResponseDTO {
    private Long companyId;
    private String compName;
    private String area;
    private String size;
    private String compinfo;
    private String field;
    private Long industryId;
    @Builder.Default
    private List<RecomendTitleDTO> recomendComps = new ArrayList<>();
}
