package jobGPT.test.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class CompInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compinfo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="company_id")
    private Company company;

    private String compinfo; // 회사 정보

    @Lob
    private String field; // 사업 구성

    @Builder
    public CompInfo(Long id, Company company, String compinfo, String field) {
        this.id = id;
        this.company = company;
        this.compinfo = compinfo;
        this.field = field;
    }
}
