package banquemisr.challenge05.tasks.management.demo.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import banquemisr.challenge05.tasks.management.demo.dtos.LoginUserDTO;
import banquemisr.challenge05.tasks.management.demo.models.Role;
import banquemisr.challenge05.tasks.management.demo.models.User;
import banquemisr.challenge05.tasks.management.demo.repositories.RoleRepository;
import banquemisr.challenge05.tasks.management.demo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthenticationService {
 private final UserRepository userRepository;
 private final RoleRepository roleRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder,
        RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User signup(LoginUserDTO input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        Role role = roleRepository.findById(input.getRoleId()).orElseThrow(()->new EntityNotFoundException("role id not found") );

        user.setRoleId(role);

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        return userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow();
    }


}
