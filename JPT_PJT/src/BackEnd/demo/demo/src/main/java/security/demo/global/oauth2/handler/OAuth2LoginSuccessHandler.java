package security.demo.global.oauth2.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import security.demo.domain.Entity.User;
import security.demo.domain.repository.UserRepository;
import security.demo.global.config.auth.PrincipalDetails;
import security.demo.global.jwt.JwtProperties;
import security.demo.global.jwt.service.JwtService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            log.info("OAuth2 Login 성공!");
            PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();
            String accessToken = jwtService.createAccessToken(oAuth2User.getUsername());
            String refreshToken = jwtService.createRefreshToken();
            response.addHeader(JwtProperties.HEADER_STRING, "Bearer " + accessToken);
            response.addHeader(JwtProperties.HEADER_REF, "Bearer " + refreshToken);

            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            User user = userRepository.findByUsername(oAuth2User.getUsername()).get();

            Date now = new Date();
            Date tokenExpirationTime = new Date(now.getTime() + JwtProperties.REFRESHTOKEN_TIME);

            user.updateRefreshToken(refreshToken,tokenExpirationTime);
            userRepository.saveAndFlush(user);

            System.out.println("name : " + user.getUsername());
            System.out.println("accessToken_oauth : " + accessToken);
            System.out.println("refreshToken_oauth : " + refreshToken);
        } catch (Exception e) {
            throw e;
        }
    }
}
