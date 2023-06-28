//package jobGPT.test.service;
//
//import jobGPT.test.domain.company.CompInfo;
//import jobGPT.test.domain.company.Company;
//import jobGPT.test.domain.company.RecomendComp;
//import jobGPT.test.domain.company.RecomendTable;
//import jobGPT.test.repository.CompanyRepository;
//import jobGPT.test.repository.CompinfoRepository;
//import jobGPT.test.repository.RecomendCompRepository;
//import jobGPT.test.repository.RecomendTableRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class ComServiceTest {
//
//    @Autowired ComService comService;
//    @Autowired CompanyRepository companyRepository;
//    @Autowired InfoService infoService;
//    @Autowired CompinfoRepository compinfoRepository;
//    @Autowired RecomendService recomendService;
//    @Autowired RecomendCompRepository recomendCompRepository;
//    @Autowired RecomendTableRepository recomendTableRepository;
//
//    @Test
//    @Rollback(false)
//    public void 회사생성() throws Exception {
//        Company company = new Company();
//        company.setCompName("삼성");
//        company.setArea("서울");
//        company.setSize("대기업");
//        companyRepository.save(company);
//        Company saveId = comService.create(company);
//
////        assertEquals(company, companyRepository.findOne(saveId));
//
//    }
//
//    @Test
//    @Rollback(false)
//    public void 회사중복() throws Exception {
//        Company company1 = new Company();
//        company1.setCompName("삼성");
//        company1.setArea("서울");
//        company1.setSize("대기업");
//        Company id1 = comService.create(company1);
//
//        Company company2 = new Company();
//        company2.setCompName("삼성");
//        company2.setArea("서울");
//        company2.setSize("대기업");
//        Company id2 = comService.create(company2);
//
//        Company company3 = new Company();
//        company3.setCompName("삼성전자");
//        company3.setArea("서울");
//        company3.setSize("대기업");
//        Company id3 = comService.create(company3);
//    }
//
//    @Test
//    @Rollback(false)
//    public void 생성() {
//        Company company1 = new Company();
//        company1.setCompName("삼성");
//        company1.setArea("서울");
//        company1.setSize("대기업");
//        Company id1 = comService.create(company1);
//
//        CompInfo compInfo = new CompInfo();
//        compInfo.setCompany(id1);
//        compInfo.setCompinfo("삼성의 정보는 이러합니다");
//        compInfo.setField("삼성의 정책은 이러합니다");
//        compinfoRepository.save(compInfo);
//
//        Company company2 = new Company();
//        company2.setCompName("삼성전자");
//        company2.setArea("서울");
//        company2.setSize("대기업");
//        Company id2 = comService.create(company2);
//
//        CompInfo compInfo2 = new CompInfo();
//        compInfo2.setCompany(id2);
//        compInfo2.setCompinfo("삼전의 정보는 이러합니다");
//        compInfo2.setField("삼전의 정책은 이러합니다");
//        compinfoRepository.save(compInfo2);
//
//        Company company3 = new Company();
//        company3.setCompName("현대");
//        company3.setArea("서울");
//        company3.setSize("대기업");
//        Company id3 = comService.create(company3);
//
//        CompInfo compInfo3 = new CompInfo();
//        compInfo3.setCompany(id3);
//        compInfo3.setCompinfo("현대의 정보는 이러합니다");
//        compInfo3.setField("현대의 정책은 이러합니다");
//        compinfoRepository.save(compInfo3);
//
//
//        List<Company> com = comService.findCom("현대");
//        System.out.println("현대에 대한 정보들");
//        System.out.println("------------------------------");
//        System.out.println(com.get(0).getCompName());
//        System.out.println(com.get(0).getArea());
//        System.out.println(com.get(0).getSize());
//        System.out.println("------------------------------");
//
//        List<CompInfo> c = infoService.findName(company3);
//        System.out.println("------------------------------");
//        System.out.println(c.get(0).getField());
//        System.out.println(c.get(0).getCompinfo());
//    }
//
//    @Test
//    @Rollback(false)
//    public void 추천() {
//        Company company1 = new Company();
//        company1.setCompName("삼성");
//        company1.setArea("서울");
//        company1.setSize("대기업");
//        Company id1 = comService.create(company1);
//
//        Company company2 = new Company();
//        company2.setCompName("현대");
//        company2.setArea("울산");
//        company2.setSize("대기업");
//        Company id2 = comService.create(company2);
//
//        ArrayList<String> recolist = new ArrayList<>(List.of("돈 많이줌","돈 많이줌" ,"MZ함", "퇴근 자유로움"));
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
//    }
//
//}