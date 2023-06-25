package security.demo.global.jwt;

public interface JwtProperties {
    String SECRET = "rlaalsrbrlatncksdbsxodndrkdgoqlsdlrlvy"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 10000 * 60; // 1분 * 60 -> 60분
    String TOKEN_PREFIX = "Bearer ";
    String TOKEN_ACC = "Authorization";
    String TOKEN_REF = "Authorization-refresh";
    String HEADER_STRING = "AccessToken";
    String HEADER_REF = "RefreshToken";
    int REFRESHTOKEN_TIME = 10000 * 60 * 24 * 3; // 1분 * 60 * 24 * 3 -> 3일
}
