package jobGPT.test.service;

import jobGPT.test.domain.CompInfo;
import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendComp;
import jobGPT.test.domain.RecomendTable;
import jobGPT.test.dto.*;
import jobGPT.test.repository.CompanyRepository;
import jobGPT.test.repository.CompinfoRepository;
import jobGPT.test.repository.RecomendCompRepository;
import jobGPT.test.repository.RecomendTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ComService {

    private final CompanyRepository companyRepository;
    private final CompinfoRepository compinfoRepository;
    private final RecomendTableRepository recomendTableRepository;
    private final RecomendCompRepository recomendCompRepository;

    @Transactional
    public CompanyResponseDTO create(CompanyRequestDTO companyDTO) { // 생성하고 해당 id 반환하기
        if (companyRepository.existsByCompName(companyDTO.getCompName()) == true) {
            List<Company> savedCompany = companyRepository.findByCompName(companyDTO.getCompName());
            CompInfo savedCompinfo = compinfoRepository.findByCompany(savedCompany.get(0));
            List<RecomendTable> sendReco = recomendTableRepository.findByCompany(savedCompany.get(0));
            List<RecomendTitleDTO> sendRecoDTO = sendReco.stream().map(o -> new RecomendTitleDTO(o.getRecomendComp().getTitle())).collect(Collectors.toList());

            return CompanyResponseDTO.builder()
                    .companyId(savedCompany.get(0).getId())
                    .area(savedCompany.get(0).getArea())
                    .size(savedCompany.get(0).getSize())
                    .field(savedCompinfo.getField())
                    .compinfo(savedCompinfo.getCompinfo())
                    .compName(savedCompany.get(0).getCompName())
                    .recomendComps(sendRecoDTO).build();
        }
        Company company = Company.builder()
                .compName(companyDTO.getCompName())
                .area(companyDTO.getArea())
                .size(companyDTO.getSize()).build();
        Company savedCompany = companyRepository.save(company);

        CompInfo compInfo = CompInfo.builder()
                .company(savedCompany)
                .field(companyDTO.getField())
                .compinfo(companyDTO.getCompinfo()).build();
        CompInfo savedCompinfo = compinfoRepository.save(compInfo);

        for (String reco : companyDTO.getRecomendComps()) {

            if (recomendCompRepository.existsByTitle(reco) == true) {
                RecomendComp savedReco = recomendCompRepository.findByTitle(reco);
                RecomendTable recomendTable = RecomendTable.builder()
                        .company(savedCompany)
                        .recomendComp(savedReco).build();
                recomendTableRepository.save(recomendTable);
            }
            else {
                RecomendComp recomendComp = RecomendComp.builder()
                        .title(reco).build();
                RecomendComp savedReco = recomendCompRepository.save(recomendComp);
                RecomendTable recomendTable = RecomendTable.builder()
                        .company(savedCompany)
                        .recomendComp(savedReco).build();
                recomendTableRepository.save(recomendTable);
            }

        }
        List<RecomendTable> sendReco = recomendTableRepository.findByCompany(savedCompany);
        List<RecomendTitleDTO> sendRecoDTO = sendReco.stream().map(o -> new RecomendTitleDTO(o.getRecomendComp().getTitle())).collect(Collectors.toList());

        return CompanyResponseDTO.builder()
                .companyId(savedCompany.getId())
                .area(savedCompany.getArea())
                .size(savedCompany.getSize())
                .field(savedCompinfo.getField())
                .compinfo(savedCompinfo.getCompinfo())
                .compName(savedCompany.getCompName())
                .recomendComps(sendRecoDTO).build();
    }


}
