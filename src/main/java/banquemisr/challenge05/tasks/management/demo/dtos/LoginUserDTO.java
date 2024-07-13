package banquemisr.challenge05.tasks.management.demo.dtos;

import lombok.Data;

@Data
public class LoginUserDTO {

    private String email;
    private String password;
    private int roleId;

}
