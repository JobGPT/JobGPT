package jobGPT.test.global.login.handler;

import jobGPT.test.domain.security.Entity.User;
import jobGPT.test.domain.security.repository.UserRepository;
import jobGPT.test.global.jwt.JwtProperties;
import jobGPT.test.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String username = extractUsername(authentication); // 인증 정보에서 Username 추출
        String accessToken = jwtService.createAccessToken(username); // JwtService의 createAccessToken을 사용하여 AccessToken 발급
        String refreshToken = jwtService.createRefreshToken(); // JwtService의 createRefreshToken을 사용하여 RefreshToken 발급

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답

        Date now = new Date();
        Date tokenExpirationTime = new Date(now.getTime() + JwtProperties.REFRESHTOKEN_TIME);

        User user = userRepository.findByUsername(username).get();
        if (userRepository.existsByUsername(username)) {
            user.updateRefreshToken(refreshToken,tokenExpirationTime);
            userRepository.saveAndFlush(user);
        }
        System.out.println("login success, username : "+ username);
        System.out.println("login success, JwtToken : "+ accessToken);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }


}
