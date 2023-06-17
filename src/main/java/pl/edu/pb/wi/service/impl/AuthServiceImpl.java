package pl.edu.pb.wi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.dto.request.AuthDtoRequest;
import pl.edu.pb.wi.dto.request.RegisterDtoRequest;
import pl.edu.pb.wi.dto.response.AuthDtoResponse;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.service.AuthService;
import pl.edu.pb.wi.service.JwtService;
import pl.edu.pb.wi.service.RoleService;
import pl.edu.pb.wi.service.UserService;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    AuthServiceImpl(
            PasswordEncoder passwordEncoder,
            UserService userService,
            RoleService roleService,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthDtoResponse register(RegisterDtoRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .role(roleService.getRoleByName(Role.RoleType.USER.getName()))
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userService.create(user);
        String token = jwtService.generateToken(user);
        return AuthDtoResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthDtoResponse authenticate(AuthDtoRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword())
        );
        User user = (User) userService.loadUserByUsername(authenticationRequest.getUsername());
        if(user == null) throw new UsernameNotFoundException("User with provided username not found");

        String token = jwtService.generateToken(user);
        return AuthDtoResponse.builder()
                .token(token)
                .build();
    }
}
