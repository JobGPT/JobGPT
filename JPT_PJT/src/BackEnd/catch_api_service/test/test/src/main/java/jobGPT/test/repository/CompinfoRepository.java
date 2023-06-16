package jobGPT.test.repository;


import jobGPT.test.domain.CompInfo;
import jobGPT.test.domain.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompinfoRepository {

    private final EntityManager em;

    public void save(CompInfo compInfo) {
        em.persist(compInfo);
    }

//    public CompInfo findOne(Long id) {
//        return em.find(CompInfo.class, id);
//    }

    public List<CompInfo> findByName(Company company) { // 회사 번호로 조회
        return em.createQuery("select c from CompInfo c where c.company = :company", CompInfo.class)
                .setParameter("company", company)
                .getResultList();
    }

}
