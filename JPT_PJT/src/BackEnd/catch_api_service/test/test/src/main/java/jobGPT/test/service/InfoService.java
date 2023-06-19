package jobGPT.test.service;

import jobGPT.test.domain.CompInfo;
import jobGPT.test.domain.Company;
import jobGPT.test.repository.CompinfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InfoService {

    private final CompinfoRepository compinfoRepository;

    public void save(CompInfo compinfo) {
        compinfoRepository.save(compinfo);
    }
//
//    public List<CompInfo> findName(Company company) {
//        return compinfoRepository.findByName(company);
//    }
}
