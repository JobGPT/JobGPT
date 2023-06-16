package jobGPT.test.repository;


import jobGPT.test.domain.CompInfo;
import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendComp;
import jobGPT.test.domain.RecomendTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecomendTableRepository {

    private final EntityManager em;

    public void save(RecomendTable recomendTable) {
    em.persist(recomendTable);
}

    public List<RecomendTable> findByCom(Company company) {
        return em.createQuery("select r from RecomendTable r where r.company= :company", RecomendTable.class).setParameter("company",company).getResultList();
    }

    public List<RecomendTable> findByReco(RecomendComp recomendComp) {
        return em.createQuery("select r from RecomendTable r where r.recomendcomp= :recomendComp", RecomendTable.class).setParameter("recomendcomp",recomendComp).getResultList();
    }
}
