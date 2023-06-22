package jobGPT.test.repository;


import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendComp;
import jobGPT.test.domain.RecomendTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecomendCompRepository extends JpaRepository<RecomendComp,Long> {
    RecomendComp findByTitle(String title);
    boolean existsByTitle(String title);

}