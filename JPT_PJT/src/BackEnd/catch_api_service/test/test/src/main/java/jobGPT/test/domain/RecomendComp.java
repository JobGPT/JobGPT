package jobGPT.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
public class RecomendComp {

    @Id
    @GeneratedValue
    @Column(name = "recomendcomp_id")
    private Long id;

    private String title; // 추천 종류 이름

    @OneToMany(mappedBy = "recomendComp")
    private List<RecomendTable> recomendTable = new ArrayList<>();

//    @Transient
//    public List<String> getRecomendations() {
//        return recomendTable.stream()
//                .map(RecomendTable::getCompany)
//                .map(Company::getCompName)
//                .collect(Collectors.toList());
//    }

}
