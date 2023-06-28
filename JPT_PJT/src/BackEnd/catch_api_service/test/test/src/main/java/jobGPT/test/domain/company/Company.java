package jobGPT.test.domain.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry; // 상세 정보
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<RecomendTable> recomendTable = new ArrayList<>(); // 추천 테이블

    @JsonIgnore
    @OneToOne(mappedBy = "company", fetch = FetchType.LAZY)
    private CompInfo compInfo; // 상세 정보

    @Builder
    public Company(Long id, String compName, String area, String size, List<RecomendTable> recomendTable, CompInfo compInfo) {
        this.id = id;
        this.compName = compName;
        this.area = area;
        this.size = size;
        this.recomendTable = recomendTable;
        this.compInfo = compInfo;
    }

}
