package security.demo.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class SignUpDto {
    private String username;
    private String password;
    private String email;
}
