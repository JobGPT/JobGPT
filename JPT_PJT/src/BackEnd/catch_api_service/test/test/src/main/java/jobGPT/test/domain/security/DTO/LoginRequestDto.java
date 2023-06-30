package jobGPT.test.domain.security.DTO;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
