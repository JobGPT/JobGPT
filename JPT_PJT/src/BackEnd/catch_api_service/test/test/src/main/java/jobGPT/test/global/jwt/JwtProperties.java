package jobGPT.test.global.jwt;

public interface JwtProperties {
    String SECRET = "rlaalsrbrlatncksdbsxodndrkdgoqlsdlrlvy"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 60000 * 30; // -> 30분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "AccessToken";
    String HEADER_REF = "RefreshToken";
    int REFRESHTOKEN_TIME = 60000 * 60 * 6; // -> 6시간
}
