package jobGPT.test.service;

import jobGPT.test.domain.Company;
import jobGPT.test.dto.CompanyDTO;
import jobGPT.test.repository.CompanyRepository;
import jobGPT.test.repository.CompinfoRepository;
import jobGPT.test.repository.RecomendCompRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ComService {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyDTO create(CompanyDTO companyDTO) { // 생성하고 해당 id 반환하기
        List<Company> findcompany = companyRepository.findByName(companyDTO.getCompName());
        if (!findcompany.isEmpty()) { // 만약 해당 이름이 이미 존재한다면
            System.out.println("이미 있어요");
            Company already = findcompany.get(0);
            return convertToDTO(already); // 해당 이름의 Company를 DTO로 변환하여 반환
        } else {
            Company company = convertToEntity(companyDTO); // DTO를 Entity로 변환
            companyRepository.save(company);
            return convertToDTO(company); // 저장된 Company를 DTO로 변환하여 반환
        }
    }

    private CompanyDTO convertToDTO(Company company) {
        // ModelMapper 또는 수동으로 DTO 객체를 생성하고 값을 설정하여 반환
        CompanyDTO dto = new CompanyDTO();
        dto.setCompName(company.getCompName());
        // 필요한 나머지 정보도 설정
        return dto;
    }

    private Company convertToEntity(CompanyDTO dto) {
        // ModelMapper 또는 수동으로 Entity 객체를 생성하고 값을 설정하여 반환
        Company entity = new Company();
        entity.setCompName(dto.getCompName());
        entity.setArea(dto.getArea());
        entity.setSize(dto.getSize());
        // 필요한 나머지 정보도 설정
        return entity;
    }

    public List<Company> findCom(String compname) {
        return companyRepository.findByName(compname);
    }


}
