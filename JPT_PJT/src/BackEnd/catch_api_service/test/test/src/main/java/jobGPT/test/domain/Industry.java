package jobGPT.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "industry")
@Getter
@Setter
public class Industry {
    @Id
    @GeneratedValue
    @Column(name="industry_code")
    private Long code;
    private String industryName;

    @OneToOne(mappedBy = "industry")
    private IndustryFigure industryFigure;


}
