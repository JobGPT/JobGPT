package jobGPT.test.service;

import jobGPT.test.domain.company.RecomendComp;
import jobGPT.test.domain.company.RecomendTable;
import jobGPT.test.dto.RecoCompDTO;
import jobGPT.test.dto.RecoCompResponseDTO;
import jobGPT.test.repository.RecomendCompRepository;
import jobGPT.test.repository.RecomendTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecomendService {

    private final RecomendCompRepository recomendCompRepository;
    private final RecomendTableRepository recomendTableRepository;

    public RecoCompResponseDTO recoList(String name) {
        RecomendComp recomendComp = recomendCompRepository.findByTitle(name);
        List<RecomendTable> recomendTables = recomendTableRepository.findByRecomendComp(recomendComp);
        List<RecoCompDTO> sendRecoDTO = recomendTables.stream().map(o ->
                new RecoCompDTO(o.getCompany().getCompName())).collect(Collectors.toList());
        return RecoCompResponseDTO.builder()
                .title(name)
                .compName(sendRecoDTO).build();
    }


}

