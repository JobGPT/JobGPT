package jobGPT.test.repository;

import jobGPT.test.domain.company.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryRepository extends JpaRepository<Industry, Long> {
    Industry findByIndustryName(String industryName);

    Industry findByCode(Long code);
}
