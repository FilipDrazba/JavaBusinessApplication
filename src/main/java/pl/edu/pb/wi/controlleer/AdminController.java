package pl.edu.pb.wi.controlleer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.controlleer.annotation.AccountType;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.entity.User;

@RestController
@ApiRequestMapping
@RequiredArgsConstructor
public class AdminController {
    @GetMapping("/admin/user/{id}")
    @AccountType(accountType = Role.RoleType.ADMIN)
    User getUserById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/admin/user/{id}")
    @AccountType(accountType = Role.RoleType.ADMIN)
    int deleteUserById(@PathVariable Long id) {
        return 0;
    }

    @PostMapping("/admin/super-user")
    @AccountType(accountType = Role.RoleType.ADMIN)
    User createSuperUser(@RequestBody User user) {
        return user;
    }
}
