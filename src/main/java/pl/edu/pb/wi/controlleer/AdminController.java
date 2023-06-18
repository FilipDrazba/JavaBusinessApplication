package pl.edu.pb.wi.controlleer;

import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.controlleer.annotation.AccountType;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.service.UserService;

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
}
