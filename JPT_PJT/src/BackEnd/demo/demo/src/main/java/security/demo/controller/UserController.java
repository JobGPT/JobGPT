package security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.demo.domain.DTO.SignUpDto;
import security.demo.domain.service.UserService;


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
