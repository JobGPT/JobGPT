package jobGPT.test.repository;

import jobGPT.test.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByCompName(String compName);
    Company findByCompName(String compName);
}
