package jobGPT.test.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class RecomendTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recomendtable_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company; // 회사와 다대일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recomendcomp_id")
    private RecomendComp recomendComp; // 추천명 과 다대일


//     연관관계 메서드
    public void setCompany(Company company) {
        this.company = company;
        company.getRecomendTable().add(this);
    }

    public void setRecomendComp(RecomendComp recomendComp) {
        this.recomendComp = recomendComp;
        recomendComp.getRecomendTable().add(this);
    }
    @Builder
    public RecomendTable(Long id, Company company, RecomendComp recomendComp) {
        this.id = id;
        this.company = company;
        this.recomendComp = recomendComp;
    }
}
