package jobGPT.test.service;

import jobGPT.test.repository.RecomendCompRepository;
import jobGPT.test.repository.RecomendTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecomendService {

    private final RecomendCompRepository recomendCompRepository;
    private final RecomendTableRepository recomendTableRepository;

//    @Transactional
//    public RecomendCompResponseDTO create(RecomendCompResponseDTO recomendCompDTO) { // 생성하고 해당 id 반환하기
//        List<RecomendComp> findReco = recomendCompRepository.findByName(recomendCompDTO.getTitle());
//        if (!findReco.isEmpty()) { // 만약 해당 이름이 이미 존재한다면
//            System.out.println("이미 존재하는 추천 항목");
//            RecomendComp existingReco = findReco.get(0);
//            return convertToDTO(existingReco); // 이미 존재하는 Entity를 DTO로 변환하여 반환
//        } else {
//            RecomendComp recomendComp = convertToEntity(recomendCompDTO); // DTO를 Entity로 변환
//            recomendCompRepository.save(recomendComp);
//            return convertToDTO(recomendComp); // 저장된 Entity를 DTO로 변환하여 반환
//        }
//    }


}

