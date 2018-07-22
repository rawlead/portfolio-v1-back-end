package us.ivanshyrai.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import us.ivanshyrai.portfolio.exception.AppException;
import us.ivanshyrai.portfolio.model.Role;
import us.ivanshyrai.portfolio.model.RoleName;
import us.ivanshyrai.portfolio.model.User;
import us.ivanshyrai.portfolio.payload.ApiResponse;
import us.ivanshyrai.portfolio.payload.JwtAuthenticationResponse;
import us.ivanshyrai.portfolio.payload.LoginRequest;
import us.ivanshyrai.portfolio.payload.SignupRequest;
import us.ivanshyrai.portfolio.repository.RoleRepository;
import us.ivanshyrai.portfolio.repository.UserRepository;
import us.ivanshyrai.portfolio.security.JwtTokenProvider;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername()))
            return new ResponseEntity<>(new ApiResponse(false,
                    "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        if (userRepository.existsByEmail(signupRequest.getEmail()))
            return new ResponseEntity<>(new ApiResponse(false,
                    "Email address is already taken!"),
                    HttpStatus.BAD_REQUEST);
        User user = new User(
                signupRequest.getName(),
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPassword()
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set"));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(
                true,
                "User registered successfully"
        ));

    }
}


























