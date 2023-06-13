package pl.edu.pb.wi.service;

import pl.edu.pb.wi.dto.request.AuthDtoRequest;
import pl.edu.pb.wi.dto.request.RegisterDtoRequest;
import pl.edu.pb.wi.dto.response.AuthDtoResponse;

public interface AuthService {
    AuthDtoResponse register(RegisterDtoRequest registerRequest);

    AuthDtoResponse authenticate(AuthDtoRequest authenticationRequest);
}
