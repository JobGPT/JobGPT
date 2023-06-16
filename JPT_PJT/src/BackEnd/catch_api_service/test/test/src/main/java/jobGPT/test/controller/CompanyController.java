//package jobGPT.test.controller;
//
//import jobGPT.test.domain.CompInfo;
//import jobGPT.test.domain.Company;
//import jobGPT.test.domain.RecomendComp;
//import jobGPT.test.domain.RecomendTable;
//import jobGPT.test.repository.CompinfoRepository;
//import jobGPT.test.repository.RecomendTableRepository;
//import jobGPT.test.service.ComService;
//import jobGPT.test.service.InfoService;
//import jobGPT.test.service.RecomendService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class CompanyController {
//
//    private final ComService comService;
//    private final RecomendService recomendService;
//    private final InfoService infoService;
//    private final RecomendTableRepository recomendTableRepository;
//    private final CompinfoRepository compinfoRepository;
//
//    ArrayList<String> comp_if = new ArrayList<>(List.of("삼성","서울" ,"대기업"));
//    ArrayList<String> comp_if2 = new ArrayList<>(List.of("현대","서울" ,"대기업"));
//    ArrayList<String> recolist = new ArrayList<>(List.of("돈 많이줌","돈 많이줌" ,"MZ함", "퇴근 자유로움"));
//
//    @Transactional
//    @GetMapping("/compinfo")
//    public Object first(@RequestParam("company") String company) {
//
//        Company company1 = new Company();
//        company1.setCompName(comp_if.get(0));
//        company1.setArea(comp_if.get(1));
//        company1.setSize(comp_if.get(2));
//        Company id1 = comService.create(company1);
//
//        for (String reco : recolist) {
//            RecomendComp recomendComp = new RecomendComp();
//            recomendComp.setTitle(reco);
//            RecomendComp rc = recomendService.create(recomendComp);
//            // 추천항목 없으면 저장 있으면 패스
//
//            RecomendTable recomendTable = new RecomendTable();
//            recomendTable.setCompany(id1);
//            recomendTable.setRecomendComp(rc);
//            recomendTableRepository.save(recomendTable);
//
//
//            List<RecomendTable> recomendTables = id1.getRecomendTable();
//            recomendTables.add(recomendTable);
//            id1.setRecomendTable(recomendTables);
////            id1.getRecomendTable().add(recomendTable);
//            // 중계 테이블 저장
//        }
//        return id1;
//    }
//
//    @Transactional
//    @GetMapping("/reco")
//    public Object second(@RequestParam("company") String company) {
//        Company company1 = new Company();
//        company1.setCompName(comp_if.get(0));
//        company1.setArea(comp_if.get(1));
//        company1.setSize(comp_if.get(2));
//
//        CompInfo compInfo = new CompInfo();
//        compInfo.setCompany(company1);
//        compInfo.setCompinfo("삼성의 정보는 이러합니다");
//        compInfo.setField("삼성의 정책은 이러합니다");
//        compinfoRepository.save(compInfo);
//
//        Company id1 = comService.create(company1);
//
//        Company company6 = new Company();
//        company6.setCompName(comp_if.get(0));
//        company6.setArea(comp_if.get(1));
//        company6.setSize(comp_if.get(2));
//
//        CompInfo compInfo6 = new CompInfo();
//        compInfo.setCompany(company1);
//        compInfo.setCompinfo("삼성의 정보는 이러합니다");
//        compInfo.setField("삼성의 정책은 이러합니다");
//        compinfoRepository.save(compInfo6);
//
//        Company id3 = comService.create(company6);
//
//        for (String reco : recolist) {
//            RecomendComp recomendComp = new RecomendComp();
//            recomendComp.setTitle(reco);
//            RecomendComp rc = recomendService.create(recomendComp);
//            // 추천항목 없으면 저장 있으면 패스
//
//            RecomendTable recomendTable = new RecomendTable();
//            recomendTable.setCompany(id1);
//            recomendTable.setRecomendComp(rc);
//            recomendTableRepository.save(recomendTable);
//            // 중계 테이블 저장
//        }
//
//        Company company3= new Company();
//        company3.setCompName(comp_if2.get(0));
//        company3.setArea(comp_if2.get(1));
//        company3.setSize(comp_if2.get(2));
//
//
//        CompInfo compInfo2 = new CompInfo();
//        compInfo2.setCompany(company3);
//        compInfo2.setCompinfo("삼성의 정보는 이러합니다");
//        compInfo2.setField("삼성의 정책은 이러합니다");
//        compinfoRepository.save(compInfo2);
//
//        Company id2 = comService.create(company3);
//
//        for (String reco : recolist) {
//            RecomendComp recomendComp = new RecomendComp();
//            recomendComp.setTitle(reco);
//            RecomendComp rc = recomendService.create(recomendComp);
//            // 추천항목 없으면 저장 있으면 패스
//
//            RecomendTable recomendTable = new RecomendTable();
//            recomendTable.setCompany(id2);
//            recomendTable.setRecomendComp(rc);
//            recomendTableRepository.save(recomendTable);
//            // 중계 테이블 저장
//        }
//
//        Company company2 = new Company();
//        company2 = comService.findCom("삼성").get(0);
//
//        List<RecomendTable> recomend = new ArrayList<>();
//        recomend = recomendService.findcomp(company2);
//        return recomend;
//    }
//
//    @Transactional
//    @GetMapping("/info")
//    public Object third(@RequestParam("company") String company) {
//
//        Company company1 = new Company();
//        company1.setCompName(comp_if2.get(0));
//        company1.setArea(comp_if2.get(1));
//        company1.setSize(comp_if2.get(2));
//
//        CompInfo compInfo = new CompInfo();
//        compInfo.setCompany(company1);
//        compInfo.setCompinfo("삼성의 정보는 이러합니다");
//        compInfo.setField("삼성의 정책은 이러합니다");
//        compinfoRepository.save(compInfo);
//        Company id1 = comService.create(company1);
//
//        Company company2 = new Company();
//        company2 = comService.findCom("현대").get(0);
//
//        List<CompInfo> info = new ArrayList<>();
//        info = infoService.findName(company2);
//        return info;
//    }
//}
