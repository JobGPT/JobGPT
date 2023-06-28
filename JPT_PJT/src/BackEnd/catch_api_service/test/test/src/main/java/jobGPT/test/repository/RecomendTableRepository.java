package jobGPT.test.repository;


import jobGPT.test.domain.company.Company;
import jobGPT.test.domain.company.RecomendComp;
import jobGPT.test.domain.company.RecomendTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecomendTableRepository extends JpaRepository<RecomendTable,Long> {
    List<RecomendTable> findByCompany(Company company);
    List<RecomendTable> findByRecomendComp(RecomendComp recomendComp);
}
