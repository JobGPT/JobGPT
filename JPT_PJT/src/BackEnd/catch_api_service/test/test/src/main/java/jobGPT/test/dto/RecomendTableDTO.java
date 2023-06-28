package jobGPT.test.dto;

import jobGPT.test.domain.company.Company;
import jobGPT.test.domain.company.RecomendComp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecomendTableDTO {
    private Company company;
    private RecomendComp recomendComp;
}
