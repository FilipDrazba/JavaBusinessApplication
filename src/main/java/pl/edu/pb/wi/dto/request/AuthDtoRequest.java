package pl.edu.pb.wi.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder
public class AuthDtoRequest {
    private String username;
    private String password;
}
