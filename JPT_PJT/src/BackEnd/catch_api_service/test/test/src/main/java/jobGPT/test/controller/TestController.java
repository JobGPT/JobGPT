package jobGPT.test.controller;

import jobGPT.test.dto.CompanyRequestDTO;
import jobGPT.test.dto.CompanyResponseDTO;
import jobGPT.test.dto.RecoCompResponseDTO;
import jobGPT.test.repository.RecomendTableRepository;
import jobGPT.test.service.ComService;
import jobGPT.test.service.RecomendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ComService comService;

    @PostMapping("/postreco")
    public ResponseEntity<CompanyResponseDTO> createCompany(CompanyRequestDTO companyRequestDTO) {
        return ResponseEntity.ok(comService.create(companyRequestDTO));
    }

    @PostMapping("/recoList/{name}")
    public ResponseEntity<RecoCompResponseDTO> SearchRecoCompany(@PathVariable("name") String name) {
        return ResponseEntity.ok(comService.recoList(name));
    } // 이거 안댐 -> 고쳐야 함 (RecomendComp constructor 문제)
}
