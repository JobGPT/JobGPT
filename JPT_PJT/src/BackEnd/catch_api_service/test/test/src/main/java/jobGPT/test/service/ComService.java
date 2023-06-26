package jobGPT.test.service;

import jobGPT.test.domain.CompInfo;
import jobGPT.test.domain.Company;
import jobGPT.test.domain.RecomendComp;
import jobGPT.test.domain.RecomendTable;
import jobGPT.test.dto.*;
import jobGPT.test.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
    private final IndustryRepository industryRepository;

    @Transactional
    public CompanyResponseDTO create(CompanyRequestDTO companyDTO) { // 생성하고 해당 id 반환하기
        if (companyRepository.existsByCompName(companyDTO.getCompName()) == true) {
            Company savedCompany = companyRepository.findByCompName(companyDTO.getCompName());
            CompInfo savedCompinfo = compinfoRepository.findByCompany(savedCompany);
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

    @Transactional
    public CompanyResponseDTO addCompany(String companyName) {
        if (companyRepository.existsByCompName(companyName) == true){
            Company company = companyRepository.findByCompName(companyName);
            return CompanyResponseDTO.builder()
                    .companyId(company.getId())
                    .area(company.getArea())
                    .size(company.getSize())
                    .compName(company.getCompName())
                    .industryId(company.getIndustry().getCode()).build();
        }
        try {
            String apiKey = "OKi0USF3nvPj8a7RqbTErJqAeUNEt0YnkpKixpoEB2QcQ";
            String apiUrl = "https://www.catch.co.kr/apiGuide/guide/openAPIGuide/apiCompList?Service=1&CompName=" + companyName + "&SortCode=1&APIKey=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);

            JSONObject jsonObj = XML.toJSONObject(response);
            JSONObject jsonCompObj = jsonObj.getJSONObject("Data").getJSONObject("Companys");
            JSONObject compData;

            if (jsonCompObj.length() == 1) {
                compData = jsonCompObj.getJSONObject("Company");
            } else {
                compData = jsonCompObj.getJSONArray("Company").getJSONObject(0);
            }

            String companyIndustryName = getIndustyName(compData);

            Company company = new Company();
            company.setCompName(compData.getString("CompName"));
            company.setSize(compData.getString("CompSizeName"));
            company.setArea(compData.getString("CompAddress"));
            company.setIndustry(industryRepository.findByIndustryName(companyIndustryName));
            companyRepository.save(company);

        } catch (Exception e) {
            System.out.println("올바른 회사이름을 입력해주세요");
            e.printStackTrace();
        }
        Company company = companyRepository.findByCompName(companyName);
        return CompanyResponseDTO.builder()
                .companyId(company.getId())
                .area(company.getArea())
                .size(company.getSize())
                .compName(company.getCompName())
                .industryId(company.getIndustry().getCode()).build();
    }
    private static String getIndustyName(JSONObject compData) {
        String industryName = compData.getString("JinhakCodeName");
        String manufacturing = "기계장비,반도체·디스플레이,생활용품·화장품,섬유·의류,식품·음료,에너지·화학" +
                ",자동차·운송장비,전기·전자,제약·바이오,조선,철강·금속,제조업 기타,운송장비 부품,목재·제지,가구·인테리어";
        String sales = "무역·상사,운송,도소매·유통";
        String architecture = "건설·토목";
        String service = "호텔·여행·항공,서비스 기타,외식업,연구개발,법률·회계·세무,리서치·컨설팅,협회·단체";
        String internet = "게임,네트워크·통신,솔루션·SI·CRM·ERP,전자상거래,포털·플랫폼";
        String banking = "은행·금융,지주회사";
        String media = "광고·홍보,미디어·문화 기타,방송·언론,영화·엔터테인먼트";
        String edu = "교육·출판,학교법인";
        String institution = "공공기관";
        String medical = "의료·보건";

        String companyIndustryName;
        if (manufacturing.contains(industryName)) {
            companyIndustryName = "제조·화학";
        } else if (sales.contains(industryName)) {
            companyIndustryName = "판매·유통";
        } else if (architecture.contains(industryName)) {
            companyIndustryName = "건설업";
        } else if (service.contains(industryName)) {
            companyIndustryName = "서비스업";
        } else if (internet.contains(industryName)) {
            companyIndustryName = "IT·웹·통신";
        } else if (banking.contains(industryName)) {
            companyIndustryName = "은행·금융업";
        } else if (media.contains(industryName)) {
            companyIndustryName = "미디어·디자인";
        } else if (edu.contains(industryName)) {
            companyIndustryName = "교육업";
        } else if (institution.contains(industryName)) {
            companyIndustryName = "기관·협회";
        } else {
            companyIndustryName = "의료·제약·복지";
        }
        return companyIndustryName;
    }


}
