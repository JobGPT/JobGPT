package jobGPT.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class IndustryFigure {

    @Id
    @GeneratedValue
    @Column(name="figure_code")
    private Long code;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_code")
    private Industry industry;
    private String industryFigure;

    @OneToOne(mappedBy = "industryFigure")
    private IndustryDetail industryDetail;

}
