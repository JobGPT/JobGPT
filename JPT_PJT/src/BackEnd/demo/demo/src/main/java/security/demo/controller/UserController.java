package security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import security.demo.domain.DTO.SignUpDto;
import security.demo.domain.Entity.User;
import security.demo.domain.service.UserService;
import security.demo.global.jwt.service.JwtService;
import security.demo.global.logout.service.LogoutService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    LogoutService logoutService;
    @Autowired
    JwtService jwtService;

    @GetMapping("/test")
    public @ResponseBody String test(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauth) {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        System.out.println("authentication: " + oAuth2User.getAttributes());
        System.out.println("oauth2user: " + oauth.getAttributes());
        return "정보 확인";
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @PostMapping("/signup")
    public String joinProc(SignUpDto signUpDto) throws Exception{
        System.out.println("회원가입 진행 : " + signUpDto);
        userService.signUp(signUpDto);
        return "redirect:/login";
    }

    @PostMapping("/search/{username}")
    public ResponseEntity<User>search(@PathVariable("username") String username) throws Exception{
        return ResponseEntity.ok(userService.search(username));
    }

}
