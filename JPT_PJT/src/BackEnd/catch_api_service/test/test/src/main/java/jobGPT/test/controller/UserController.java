package jobGPT.test.controller;


import jobGPT.test.domain.security.DTO.SignUpDto;
import jobGPT.test.domain.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> joinProc(SignUpDto signUpDto) throws Exception{
        System.out.println("회원가입 진행 : " + signUpDto);
        userService.signUp(signUpDto);
        return ResponseEntity.ok("signup success");
    }
}
