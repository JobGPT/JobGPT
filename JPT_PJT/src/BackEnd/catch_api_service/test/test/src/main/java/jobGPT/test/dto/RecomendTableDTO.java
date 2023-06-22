package jobGPT.test.dto;

import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendComp;
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
