package pl.edu.pb.wi.dto.response;

import pl.edu.pb.wi.entity.Role;

public record UserDtoResponse(Long id,
                              Role role,
                              String username,
                              String email,
                              String firstName,
                              String lastName) {
}
