package jobGPT.test.global.logout.handler;

import jobGPT.test.global.jwt.service.JwtService;
import jobGPT.test.global.logout.service.LogoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
@Component
@Slf4j
@RequiredArgsConstructor
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private final JwtService jwtService;
    private final LogoutService logoutService;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (request.getHeader("AccessToken") == null) {
            String authorizationHeader = request.getHeader("RefreshToken");
            String token = authorizationHeader.split(" ")[1];
            logoutService.logoutByRefresh(token);
            log.info("logout Success by refreshtoken");
        } else {
            String authorizationHeader = request.getHeader("AccessToken");
            String token = authorizationHeader.split(" ")[1];
            Optional<String> username = jwtService.extractUsername(token);
            logoutService.logoutByUsername(username.get());
            log.info("logout Success by username");
        }
    }
}
