package jobGPT.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class RecomendTable {

    @Id
    @GeneratedValue
    @Column(name="recomendtable_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company; // 회사와 다대일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recomendcomp_id")
    private RecomendComp recomendComp; // 추천명 과 다대일


    // 연관관계 메서드
//    public void setCompany(Company company) {
//        this.company = company;
//        company.getRecomendTable().add(this);
//    }
//
//    public void setReco(RecomendComp recomendComp) {
//        this.recomendComp = recomendComp;
//        recomendComp.getRecomendTable().add(this);
//    }

    // 회사별 추천 항목 메서드
//    public static RecomendTable createReco(Company company, RecomendComp... recos) {
//        RecomendTable recomendTable = new RecomendTable();
//        recomendTable.setCompany(company);
//        recomendTable.setReco(recos);
//    }

}
