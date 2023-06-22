package pl.edu.pb.wi.controlleer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(AdminController.class);

    AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/user/{id}")
    @AccountType(accountType = Role.RoleType.ADMIN)
    User getUserById(@PathVariable Long id) {
        logger.info("Received request to fetch user by ID: {}", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/admin/user/{id}")
    @AccountType(accountType = Role.RoleType.ADMIN)
    int deleteUserById(@PathVariable Long id) {
        logger.info("Received request to delete user by ID: {}", id);
        return userService.deleteUserById(id);
    }

    @PostMapping("/admin/moderator")
    @AccountType(accountType = Role.RoleType.ADMIN)
    User createModerator(@RequestBody User user) {
        logger.info("Received request to create moderator: {}", user);
        userService.createModerator(user);
        logger.info("Moderator created successfully");
        return user;
    }

    @GetMapping("/admin/users")
    @AccountType(accountType = Role.RoleType.ADMIN)
    List<User> getAllUsers() {
        return userService.getAllUsers();
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

    @PutMapping("admin/user/{id}")
    @AccountType(accountType = Role.RoleType.ADMIN)
    ResponseEntity<UserDtoResponse> updateUser(@PathVariable Long id,
                                               @RequestBody UserDtoRequest userDtoRequest) {
        User savedUser = userService.update(id, UserDtoMapper.INSTANCE.fromUserDtoRequestToUser(userDtoRequest));
        return ResponseEntity.ok(UserDtoMapper.INSTANCE.fromUserToUserDtoResponse(savedUser));
    }
}
