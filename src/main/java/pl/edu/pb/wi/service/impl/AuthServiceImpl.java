package pl.edu.pb.wi.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.dto.request.AuthDtoRequest;
import pl.edu.pb.wi.dto.request.RegisterDtoRequest;
import pl.edu.pb.wi.dto.response.AuthDtoResponse;
import pl.edu.pb.wi.entity.Basket;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.service.*;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BasketService basketService;

    @Autowired
    AuthServiceImpl(
            PasswordEncoder passwordEncoder,
            UserService userService,
            RoleService roleService,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            BasketService basketService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.basketService = basketService;
    }

    @Override
    public AuthDtoResponse register(RegisterDtoRequest registerRequest) {
        logger.info("Registering a new user: {}", registerRequest.getUsername());
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .role(roleService.getRoleByName(Role.RoleType.USER.getName()))
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        Basket basket = Basket.builder().user(user).build();

        userService.createSimpleUser(user);
        basketService.create(basket);

        String token = jwtService.generateToken(user);
        logger.info("User registered successfully: {}", registerRequest.getUsername());
        return AuthDtoResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthDtoResponse authenticate(AuthDtoRequest authenticationRequest) {
        logger.info("Authenticating user: {}", authenticationRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword())
        );
        User user = (User) userService.loadUserByUsername(authenticationRequest.getUsername());
        if(user == null) throw new UsernameNotFoundException("User with provided username not found");

        String token = jwtService.generateToken(user);
        logger.info("User authenticated successfully: {}", authenticationRequest.getUsername());
        return AuthDtoResponse.builder()
                .token(token)
                .build();
    }
}
