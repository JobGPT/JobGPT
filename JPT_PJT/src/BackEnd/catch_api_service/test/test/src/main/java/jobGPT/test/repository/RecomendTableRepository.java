package jobGPT.test.repository;


import jobGPT.test.domain.CompInfo;
import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendComp;
import jobGPT.test.domain.RecomendTable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface RecomendTableRepository extends JpaRepository<RecomendTable,Long> {
    List<RecomendTable> findByCompany(Company company);
    List<RecomendTable> findByRecomendComp(RecomendComp recomendComp);
}
