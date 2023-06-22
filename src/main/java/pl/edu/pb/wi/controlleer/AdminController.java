package pl.edu.pb.wi.controlleer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.controlleer.annotation.AccountType;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.dto.request.UserDtoRequest;
import pl.edu.pb.wi.dto.response.UserDtoResponse;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.mapper.UserDtoMapper;
import pl.edu.pb.wi.service.UserService;

import java.util.List;

@RestController
@ApiRequestMapping
public class AdminController {
    private final UserService userService;

    AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/user/{id}")
    @AccountType(accountType = Role.RoleType.ADMIN)
    User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/admin/user/{id}")
    @AccountType(accountType = Role.RoleType.ADMIN)
    int deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @PostMapping("/admin/moderator")
    @AccountType(accountType = Role.RoleType.ADMIN)
    User createModerator(@RequestBody User user) {
        userService.createModerator(user);
        return user;
    }

    @GetMapping("/admin/moderators")
    @AccountType(accountType = Role.RoleType.ADMIN)
    ResponseEntity<List<UserDtoResponse>> getAllModerators() {
        return ResponseEntity.ok(userService.getAllModerators().stream()
                .map(UserDtoMapper.INSTANCE::fromUserToUserDtoResponse)
                .toList());
    }

    @PostMapping("/admin/user")
    @AccountType(accountType = Role.RoleType.ADMIN)
    ResponseEntity<UserDtoResponse> createUser(@RequestBody UserDtoRequest userDtoRequest) {
        User savedUser = userService.createSimpleUser(UserDtoMapper.INSTANCE.fromUserDtoRequestToUser(userDtoRequest));
        return ResponseEntity.ok(UserDtoMapper.INSTANCE.fromUserToUserDtoResponse(savedUser));
    }
}
