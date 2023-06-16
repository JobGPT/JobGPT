package jobGPT.test.repository;

import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendComp;
import jobGPT.test.domain.RecomendTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecomendCompRepository {

    private final EntityManager em;

    public void save(RecomendComp recomendComp) {
        em.persist(recomendComp);
    }

    public RecomendComp findOne(String name) { // 이름으로 조회
        return em.find(RecomendComp.class,name);
    }

    public List<RecomendComp> findByName(String title) { // 이름으로 조회
        return em.createQuery("select c from RecomendComp c where c.title= :title",RecomendComp.class).setParameter("title",title).getResultList();
    }

}