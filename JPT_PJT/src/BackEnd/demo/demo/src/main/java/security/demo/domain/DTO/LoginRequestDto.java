package security.demo.domain.DTO;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
