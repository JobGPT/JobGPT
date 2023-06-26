package security.demo.global.jwt;

public interface JwtProperties {
    String SECRET = "rlaalsrbrlatncksdbsxodndrkdgoqlsdlrlvy"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 30000 ; // -> 30초
    String TOKEN_PREFIX = "Bearer ";
//    String TOKEN_ACC = "Authorization";
//    String TOKEN_REF = "Authorization-refresh";
    String HEADER_STRING = "AccessToken";
    String HEADER_REF = "RefreshToken";
    int REFRESHTOKEN_TIME = 60000 * 5; // -> 5분
}
