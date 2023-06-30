package jobGPT.test.controller;

import jobGPT.test.domain.Company;
import jobGPT.test.dto.*;

import jobGPT.test.dto.CompanyRequestDTO;
import jobGPT.test.dto.CompanyResponseDTO;
import jobGPT.test.dto.PostingDTO;
import jobGPT.test.dto.RecoCompResponseDTO;

import jobGPT.test.repository.CompanyRepository;
import jobGPT.test.repository.IndustryRepository;
import jobGPT.test.service.ComService;
import jobGPT.test.service.RecomendService;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final ComService comService;
    private final RecomendService recomendService;
    private final CompanyRepository companyRepository;
    private final IndustryRepository industryRepository;

    @PostMapping("/postreco")
    public ResponseEntity<CompanyResponseDTO> createCompany(CompanyRequestDTO companyRequestDTO) {
        return ResponseEntity.ok(comService.create(companyRequestDTO));
    }

    @PostMapping("/recoList/{name}")
    public ResponseEntity<RecoCompResponseDTO> SearchRecoCompany(@PathVariable("name") String name) {
        return ResponseEntity.ok(recomendService.recoList(name));
    }
    /*
    **** catch API 회사 정보 가져오기
     */
    @PostMapping("/catch/{companyName}")
    @ResponseBody
    public ResponseEntity<CatchRequestDTO> getCompanyFromCatchAPI(@PathVariable String companyName) {
        CatchRequestDTO companyResponseDTO = comService.addCompany(companyName);
        if (companyResponseDTO != null) {
            return ResponseEntity.ok(companyResponseDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    /*
     **** saramin API 채용공고 가져오기
     */
    @GetMapping("/saramin/{companyName}")
    @ResponseBody
    public ResponseEntity<String> getPostingFromSaraminAPI(@PathVariable String companyName) {
        List<JSONObject> postingList = new ArrayList<>();
        try {
            String apiKey = "ozkKjcwlTgbbugp6v7q54ql1YbRlvMAU7Zy4ieMmAJCkfiC";
            String apiUrl = "https://oapi.saramin.co.kr/job-search?access-key=" + apiKey + "&ind_cd="
                    + companyRepository.findByCompName(companyName).getIndustry().getCode();

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);

            JSONObject responseToJSON = new JSONObject(response);
            JSONArray postings = responseToJSON.getJSONObject("jobs").getJSONArray("job");

            // JSON 배열 순회
            for (int i = 0; i < postings.length(); i++) {
                // JSON 객체 추출
                JSONObject jsonObject = postings.getJSONObject(i);
                String name = jsonObject.getJSONObject("company").getJSONObject("detail").getString("name");
                String href = jsonObject.getJSONObject("company").getJSONObject("detail").getString("href");
                String title = jsonObject.getJSONObject("position").getString("title");

                PostingDTO postingDTO = new PostingDTO(name, href, title);
                postingList.add(postingDTO.toJSON());
            }
        } catch (Exception e) {
            System.out.println("올바른 회사이름을 입력해주세요");
            e.printStackTrace();
        }
        return ResponseEntity.ok(postingList.toString());
    }

}
