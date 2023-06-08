package pl.edu.pb.wi.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDtoRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
}
