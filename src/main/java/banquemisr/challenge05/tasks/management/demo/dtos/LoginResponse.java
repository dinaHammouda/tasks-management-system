package banquemisr.challenge05.tasks.management.demo.dtos;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;
}
