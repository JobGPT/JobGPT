package jobGPT.test.service;

import jobGPT.test.domain.CompInfo;
import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendComp;
import jobGPT.test.domain.RecomendTable;
import jobGPT.test.dto.RecomendCompDTO;
import jobGPT.test.dto.RecomendTableDTO;
import jobGPT.test.repository.CompinfoRepository;
import jobGPT.test.repository.RecomendCompRepository;
import jobGPT.test.repository.RecomendTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecomendService {

    private final RecomendCompRepository recomendCompRepository;
    private final RecomendTableRepository recomendTableRepository;

    @Transactional
    public RecomendCompDTO create(RecomendCompDTO recomendCompDTO) { // 생성하고 해당 id 반환하기
        List<RecomendComp> findReco = recomendCompRepository.findByName(recomendCompDTO.getTitle());
        if (!findReco.isEmpty()) { // 만약 해당 이름이 이미 존재한다면
            System.out.println("이미 존재하는 추천 항목");
            RecomendComp existingReco = findReco.get(0);
            return convertToDTO(existingReco); // 이미 존재하는 Entity를 DTO로 변환하여 반환
        } else {
            RecomendComp recomendComp = convertToEntity(recomendCompDTO); // DTO를 Entity로 변환
            recomendCompRepository.save(recomendComp);
            return convertToDTO(recomendComp); // 저장된 Entity를 DTO로 변환하여 반환
        }
    }

    private RecomendCompDTO convertToDTO(RecomendComp recomendComp) {
        // ModelMapper 또는 수동으로 DTO 객체를 생성하고 값을 설정하여 반환
        RecomendCompDTO dto = new RecomendCompDTO();
        dto.setTitle(recomendComp.getTitle());
        // 필요한 나머지 정보도 설정
        return dto;
    }

    private RecomendComp convertToEntity(RecomendCompDTO dto) {
        // ModelMapper 또는 수동으로 Entity 객체를 생성하고 값을 설정하여 반환
        RecomendComp entity = new RecomendComp();
        entity.setTitle(dto.getTitle());
        // 필요한 나머지 정보도 설정
        return entity;
    }



    public RecomendTableDTO save(RecomendTableDTO recomendTableDTO) {
        RecomendTable recomendTable = convertToEntity(recomendTableDTO); // DTO를 Entity로 변환
        recomendTableRepository.save(recomendTable); // Repository에 Entity 저장
        return recomendTableDTO; // 저장된 Entity를 DTO로 변환하여 반환
    }

    private RecomendTableDTO convertToDTO(RecomendTable recomendTable) {
        // ModelMapper 또는 수동으로 DTO 객체를 생성하고 값을 설정하여 반환
        RecomendTableDTO dto = new RecomendTableDTO();
        dto.setCompany(recomendTable.getCompany());
        dto.setRecomendComp(recomendTable.getRecomendComp());
        // 필요한 나머지 정보도 설정
        return dto;
    }

    private RecomendTable convertToEntity(RecomendTableDTO dto) {
        // ModelMapper 또는 수동으로 Entity 객체를 생성하고 값을 설정하여 반환
        RecomendTable entity = new RecomendTable();
        entity.setCompany(dto.getCompany());
        entity.setRecomendComp(dto.getRecomendComp());
        // 필요한 나머지 정보도 설정
        return entity;
    }

    public List<RecomendTable> findcomp(Company company) {
        return recomendTableRepository.findByCom(company);
    }
}

