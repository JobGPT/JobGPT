package jobGPT.test.controller;

import jobGPT.test.domain.Company;
import jobGPT.test.dto.CompanyRequestDTO;
import jobGPT.test.dto.CompanyResponseDTO;
import jobGPT.test.dto.RecoCompResponseDTO;
import jobGPT.test.repository.CompanyRepository;
import jobGPT.test.service.ComService;
import jobGPT.test.service.RecomendService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final ComService comService;
    private final RecomendService recomendService;
    private final CompanyRepository companyRepository;

    @PostMapping("/postreco")
    public ResponseEntity<CompanyResponseDTO> createCompany(CompanyRequestDTO companyRequestDTO) {
        return ResponseEntity.ok(comService.create(companyRequestDTO));
    }

    @PostMapping("/recoList/{name}")
    public ResponseEntity<RecoCompResponseDTO> SearchRecoCompany(@PathVariable("name") String name) {
        return ResponseEntity.ok(recomendService.recoList(name));
    }

    @GetMapping("company/{companyName}")
    @ResponseBody
    public Map<String, Object> fetchCompanyFromCatchAPI(@PathVariable String companyName) {
        Map<String, Object> map = new HashMap<>();
        try {
            String apiKey = "OKi0USF3nvPj8a7RqbTErJqAeUNEt0YnkpKixpoEB2QcQ";
            String apiUrl = "https://www.catch.co.kr/apiGuide/guide/openAPIGuide/apiCompList?Service=1&CompName=" + companyName + "&SortCode=1&APIKey=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();

            String response = restTemplate.getForObject(apiUrl, String.class);

            JSONObject jobj = XML.toJSONObject(response);
            System.out.println(jobj.toString());

            JSONObject jobj1 = jobj.getJSONObject("Data").getJSONObject("Companys");
            JSONArray jarr = jobj1.getJSONArray("Company");

            System.out.println(jarr.get(0));
            System.out.println(jarr.get(0).getClass().getSimpleName());

            JSONObject compData = jarr.getJSONObject(0);
            System.out.println(compData);
            System.out.println(compData.getClass().getSimpleName());
            System.out.println(compData.getString("CompName"));

            // DB에 데이터 추가
            Company company = new Company();
            System.out.println("--------1------------");
            company.setCompName(compData.getString("CompName"));
            System.out.println("--------2------------");
            company.setSize(compData.getString("CompSizeName"));
            System.out.println("--------3------------");
            company.setArea(compData.getString("CompAddress"));
            System.out.println("--------4------------");

            companyRepository.save(company);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
