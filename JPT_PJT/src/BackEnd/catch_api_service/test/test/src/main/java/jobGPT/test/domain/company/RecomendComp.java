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
@Getter @Setter
@Builder
@NoArgsConstructor
public class RecomendComp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recomendcomp_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String title; // 추천 종류 이름

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "recomendComp")
    private List<RecomendTable> recomendTable = new ArrayList<>();

    public RecomendComp(Long id, String title, List<RecomendTable> recomendTable) {
        this.id = id;
        this.title = title;
        this.recomendTable = recomendTable;
    }
}
