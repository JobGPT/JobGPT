package jobGPT.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    @OneToMany(mappedBy = "industry")
    private List<Company> company = new ArrayList<>(); // 상세 정보

    @OneToOne(mappedBy = "industry")
    private IndustryFigure industryFigure;

}
