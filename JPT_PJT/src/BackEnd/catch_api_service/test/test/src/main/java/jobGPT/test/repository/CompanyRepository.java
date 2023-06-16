package jobGPT.test.repository;

import jobGPT.test.domain.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {

    private final EntityManager em;

    public void save(Company company) {
        em.persist(company);
    } // 저장

    public Company findOne(Long id) {
        return em.find(Company.class, id); // id로 회사 정보 받아오기
    }

    public List<Company> findByName(String compName) { // 이름으로 조회
        return em.createQuery("select c from Company c where c.compName= :compName",Company.class).setParameter("compName",compName).getResultList();
    }

    public Long getByName(String compName) { // 회사 id 가져오기
        return em.createQuery("select c.company_id from Company c where c.compName= :compName",Long.class).setParameter("compName",compName).getSingleResult();
    }

}
