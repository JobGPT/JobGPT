package jobGPT.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="company")
@Getter @Setter
public class Company {

    @Id
    @GeneratedValue
    @Column(name="company_id")
    private Long id;

    private String compName; // 회사이름
    private String area; // 회사 지역
    private String size; // 회사 규모

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<RecomendTable> recomendTable = new ArrayList<>(); // 추천 테이블

    @OneToOne(mappedBy = "company", fetch = FetchType.LAZY)
    private CompInfo compInfo; // 상세 정보

    // ------------------------------------------



    // 후에 industry와 연결
//    private String figureCode;

}
