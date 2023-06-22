package jobGPT.test.repository;


import jobGPT.test.domain.CompInfo;
import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CompinfoRepository extends JpaRepository<CompInfo,Long> {
    CompInfo findByCompany(Company company);

}
