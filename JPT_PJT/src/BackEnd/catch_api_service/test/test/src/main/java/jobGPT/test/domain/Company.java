package jobGPT.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="company")
@Getter @Setter
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String compName; // 회사이름
    @Column(nullable = false, length = 50)
    private String area; // 회사 지역
    @Column(nullable = false, length = 20)
    private String size; // 회사 규모

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "industry_id", referencedColumnName = "industry_code")
    private Industry industry; // 상세 정보
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<RecomendTable> recomendTable = new ArrayList<>(); // 추천 테이블

    @JsonIgnore
    @OneToOne(mappedBy = "company", fetch = FetchType.LAZY)
    private CompInfo compInfo; // 상세 정보

    @Builder
    public Company(Long id, String compName, String area, String size, Industry industry, List<RecomendTable> recomendTable, CompInfo compInfo) {
        this.id = id;
        this.compName = compName;
        this.area = area;
        this.size = size;
        this.recomendTable = recomendTable;
        this.compInfo = compInfo;
        this.industry = industry;
    }

}
