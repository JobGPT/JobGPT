package jobGPT.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Company company; // 상세 정보
    @OneToOne(mappedBy = "industry")
    private IndustryFigure industryFigure;

    public Industry(Long code, String industryName, Company company, IndustryFigure industryFigure) {
        this.code = code;
        this.industryName = industryName;
        this.company = company;
        this.industryFigure = industryFigure;
    }
}
