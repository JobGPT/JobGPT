package jobGPT.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class CompInfo {

    @Id
    @GeneratedValue
    @Column(name = "compinfo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="company_id")
    private Company company;

    private String compinfo; // 회사 정보

    private String field; // 사업 구성
}
