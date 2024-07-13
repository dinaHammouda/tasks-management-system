package banquemisr.challenge05.tasks.management.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banquemisr.challenge05.tasks.management.demo.dtos.LoginResponse;
import banquemisr.challenge05.tasks.management.demo.dtos.LoginUserDTO;
import banquemisr.challenge05.tasks.management.demo.models.User;
import banquemisr.challenge05.tasks.management.demo.services.AuthenticationService;
import banquemisr.challenge05.tasks.management.demo.services.JwtService;



@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody LoginUserDTO registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
      

        LoginResponse loginResponse = new LoginResponse();//.setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

}
