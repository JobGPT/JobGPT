package jobGPT.test.repository;


import jobGPT.test.domain.CompInfo;
import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CompinfoRepository extends JpaRepository<CompInfo,Long> {
    CompInfo findByCompany(Company company);

//    private final EntityManager em;
//
//    public void save(CompInfo compInfo) {
//        em.persist(compInfo);
//    }
//
//    public List<CompInfo> findByName(Company company) { // 회사 번호로 조회
//        return em.createQuery("select c from CompInfo c where c.company = :company", CompInfo.class)
//                .setParameter("company", company)
//                .getResultList();
//    }

}
