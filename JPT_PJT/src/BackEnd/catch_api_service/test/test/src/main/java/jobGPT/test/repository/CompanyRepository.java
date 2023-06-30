package jobGPT.test.repository;

import jobGPT.test.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByCompName(String compName);
    Company findByCompName(String compName);
    Company findFirstByOrderByIdDesc();
}
