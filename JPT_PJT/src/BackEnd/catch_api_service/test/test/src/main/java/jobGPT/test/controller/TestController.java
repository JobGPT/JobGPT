package jobGPT.test.controller;

import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendComp;
import jobGPT.test.domain.RecomendTable;
import jobGPT.test.dto.CompanyDTO;
import jobGPT.test.dto.RecomendCompDTO;
import jobGPT.test.repository.CompinfoRepository;
import jobGPT.test.repository.RecomendTableRepository;
import jobGPT.test.service.ComService;
import jobGPT.test.service.InfoService;
import jobGPT.test.service.RecomendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ComService comService;
    private final RecomendService recomendService;
    private final RecomendTableRepository recomendTableRepository;
    ArrayList<String> comp_if = new ArrayList<>(List.of("삼성","서울" ,"대기업"));
    ArrayList<String> recolist = new ArrayList<>(List.of("돈 많이줌","돈 많이줌" ,"MZ함", "퇴근 자유로움"));
    @PostMapping("/posttest/{test}")
    public ResponseEntity<CompanyDTO> first(@PathVariable(name = "test") String test) {

        CompanyDTO cDTO = new CompanyDTO();
        cDTO.setCompName(test);
        cDTO.setArea(comp_if.get(1));
        cDTO.setSize(comp_if.get(2));
        comService.create(cDTO);
        return new ResponseEntity<>(cDTO, HttpStatus.OK);
    }


    @PostMapping("/postreco/{test}")
    public ResponseEntity<CompanyDTO> second(@PathVariable(name = "test") String test) {

        CompanyDTO cDTO = new CompanyDTO();
        cDTO.setCompName(test);
        cDTO.setArea(comp_if.get(1));
        cDTO.setSize(comp_if.get(2));
        comService.create(cDTO);

        for (String reco : recolist) {
            RecomendCompDTO recomendCompDTO = new RecomendCompDTO();
            recomendCompDTO.setTitle(reco);
            RecomendCompDTO rc = recomendService.create(recomendCompDTO);
            // 추천항목 없으면 저장 있으면 패스

            RecomendTable recomendTable = new RecomendTable();
            recomendTable.setCompany(id1);
            recomendTable.setRecomendComp(rc);
            recomendTableRepository.save(recomendTable);


            List<RecomendTable> recomendTables = id1.getRecomendTable();
            recomendTables.add(recomendTable);
            id1.setRecomendTable(recomendTables);
//            id1.getRecomendTable().add(recomendTable);
            // 중계 테이블 저장
        }



        return new ResponseEntity<>(cDTO, HttpStatus.OK);
    }
}
