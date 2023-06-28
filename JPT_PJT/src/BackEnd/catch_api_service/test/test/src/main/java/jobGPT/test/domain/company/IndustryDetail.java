package jobGPT.test.domain.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class IndustryDetail {
    @Id
    @GeneratedValue
    @Column(name="detail_code")
    private Long code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "figure_code")
    private IndustryFigure industryFigure;

    private String industryDetail;
}
