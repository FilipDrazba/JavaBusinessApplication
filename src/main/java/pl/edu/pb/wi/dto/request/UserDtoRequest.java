package pl.edu.pb.wi.dto.request;

import pl.edu.pb.wi.entity.Role;

public record UserDtoRequest(String username,
                             String email,
                             String password,
                             String firstName,
                             String lastName) {
}
