package jobGPT.test.repository;


import jobGPT.test.domain.company.CompInfo;
import jobGPT.test.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompinfoRepository extends JpaRepository<CompInfo,Long> {
    CompInfo findByCompany(Company company);

}
