package jobGPT.test.dto;

import jobGPT.test.domain.Company;
import jobGPT.test.domain.Industry;
import jobGPT.test.repository.CompanyRepository;
import jobGPT.test.repository.IndustryRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class CatchRequestDTO {
    private String compName;
    private String area;
    private String size;
    private String industryName;

    @Builder
    public CatchRequestDTO(String compName, String area, String size, String industryName) {
        this.compName = compName;
        this.area = area;
        this.size = size;
        this.industryName = industryName;
    }
    public Company toEntity(IndustryRepository industryRepository) {
        Industry industry = industryRepository.findByIndustryName(industryName);

        return Company.builder()
                .compName(compName)
                .area(area)
                .size(size)
                .industry(industry)
                .build();
    }
}
