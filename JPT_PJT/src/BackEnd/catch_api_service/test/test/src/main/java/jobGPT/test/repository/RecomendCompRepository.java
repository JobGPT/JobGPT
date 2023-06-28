package jobGPT.test.repository;


import jobGPT.test.domain.company.RecomendComp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecomendCompRepository extends JpaRepository<RecomendComp,Long> {
    RecomendComp findByTitle(String title);
    boolean existsByTitle(String title);

}