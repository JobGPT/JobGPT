package security.demo.config.jwt;

public interface JwtProperties {
    String SECRET = "윤태웅바보"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 10000 * 60; // 1분 * 60 -> 60분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
