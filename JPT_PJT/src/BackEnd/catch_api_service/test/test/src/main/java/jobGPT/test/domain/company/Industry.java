package jobGPT.test.domain.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "industry")
@Getter
@Setter
@NoArgsConstructor
public class Industry {
    @Id
    @GeneratedValue
    @Column(name="industry_code")
    private Long code;
    private String industryName;
    @OneToOne(mappedBy = "industry")
    private Company company; // 상세 정보
    @OneToOne(mappedBy = "industry")
    private IndustryFigure industryFigure;


}
