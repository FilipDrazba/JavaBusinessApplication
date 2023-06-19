package pl.edu.pb.wi.controlleer;

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

    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthDtoResponse> register(@RequestBody RegisterDtoRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthDtoResponse> authenticate(@RequestBody AuthDtoRequest authenticationRequest) {
        try {
            return ResponseEntity.ok(authService.authenticate(authenticationRequest));
        } catch (BadCredentialsException exception) {
            throw new RegularException("Bad Credentials");
        }
    }
}
