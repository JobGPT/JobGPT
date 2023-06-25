package security.demo.global.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import security.demo.domain.Entity.User;
import security.demo.domain.repository.UserRepository;
import security.demo.global.jwt.JwtProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    private final UserRepository userRepository;

    public String createAccessToken(String username) {
        Date now = new Date();
        return JWT.create()
                .withSubject(JwtProperties.HEADER_STRING)
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
                .withClaim("username", username)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(JwtProperties.HEADER_REF)
                .withExpiresAt(new Date(now.getTime() + JwtProperties.REFRESHTOKEN_TIME))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(JwtProperties.HEADER_STRING, accessToken);
//        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(JwtProperties.HEADER_STRING, accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(JwtProperties.HEADER_REF, refreshToken);
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(JwtProperties.TOKEN_REF))
                .filter(refreshToken -> refreshToken.startsWith(JwtProperties.TOKEN_PREFIX))
                .map(refreshToken -> refreshToken.replace(JwtProperties.TOKEN_PREFIX, ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(JwtProperties.TOKEN_ACC ))
                .filter(accessToken -> accessToken.startsWith(JwtProperties.TOKEN_PREFIX))
                .map(accessToken -> accessToken.replace(JwtProperties.TOKEN_PREFIX, ""));
    }

    public Optional<String> extractUsername(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim("username") // claim(username) 가져오기
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public void updateRefreshToken(String username, String refreshToken) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

}
