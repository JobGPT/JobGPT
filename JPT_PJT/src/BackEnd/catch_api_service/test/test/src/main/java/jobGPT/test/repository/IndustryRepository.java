package jobGPT.test.repository;

import jobGPT.test.domain.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryRepository extends JpaRepository<Industry, Long> {
    Industry findByIndustryName(String industryName);
}
