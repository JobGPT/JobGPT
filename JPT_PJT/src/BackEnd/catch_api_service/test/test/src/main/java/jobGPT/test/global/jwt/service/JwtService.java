package jobGPT.test.global.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Jwts;
import jobGPT.test.domain.security.Entity.User;
import jobGPT.test.domain.security.repository.UserRepository;
import jobGPT.test.global.jwt.JwtProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
        return Optional.ofNullable(request.getHeader(JwtProperties.HEADER_REF))
                .filter(refreshToken -> refreshToken.startsWith(JwtProperties.TOKEN_PREFIX))
                .map(refreshToken -> refreshToken.replace(JwtProperties.TOKEN_PREFIX, ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(JwtProperties.HEADER_STRING ))
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
            log.error("there is not username from accesstoken.");
            return Optional.empty();
        }
    }

    public boolean isAccessTokenValid(String token) {
        try {
//            JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
            Jwts.parser().setSigningKey(JwtProperties.SECRET.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("not valid access token : ", e.getMessage());
            return false;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("token is not valid : ", e.getMessage());
            return false;
        }
    }

    public boolean TimeToRefresh(User user) {
        LocalDateTime time = user.getTokenExpirationTime();
        if (LocalDateTime.now().isBefore(time)) {
            return true;
        }
        return false;

    }


}
