package pl.edu.pb.wi.controlleer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.dto.request.AuthDtoRequest;
import pl.edu.pb.wi.dto.request.RegisterDtoRequest;
import pl.edu.pb.wi.dto.response.AuthDtoResponse;
import pl.edu.pb.wi.exceptions.RegularException;
import pl.edu.pb.wi.service.AuthService;

@RestController
@ApiRequestMapping
public class AuthController {
    private final AuthService authService;
    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthDtoResponse> register(@RequestBody RegisterDtoRequest registerRequest) {
        logger.info("Received registration request: {}", registerRequest);
        AuthDtoResponse response = authService.register(registerRequest);
        logger.info("User registered successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthDtoResponse> authenticate(@RequestBody AuthDtoRequest authenticationRequest) {
        logger.info("Received authentication request: {}", authenticationRequest);
        try {
            AuthDtoResponse response = authService.authenticate(authenticationRequest);
            logger.info("User authenticated successfully: {}", response);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException exception) {
            logger.error("Authentication failed: Bad Credentials");
            throw new RegularException("Bad Credentials");
        }
    }
}
