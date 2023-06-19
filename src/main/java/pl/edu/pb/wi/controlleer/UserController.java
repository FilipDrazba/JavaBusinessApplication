package pl.edu.pb.wi.controlleer;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.entity.User;

@RestController
@ApiRequestMapping
public class UserController {

    @GetMapping("user")
    @Transactional
    User getUserDetails() {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authenticatedUser.setPassword(null);

        return authenticatedUser;
    }
}
