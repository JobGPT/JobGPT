package jobGPT.test.domain.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class IndustryFigure {

    @Id
    @GeneratedValue
    @Column(name="figure_code")
    private Long code;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id", referencedColumnName = "industry_code")
    private Industry industry;
    private String industryFigure;

    @OneToOne(mappedBy = "industryFigure")
    private IndustryDetail industryDetail;

}
