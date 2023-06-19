package jobGPT.test.repository;

import jobGPT.test.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByCompName(String compName);
    List<Company> findByCompName(String compName);
}
