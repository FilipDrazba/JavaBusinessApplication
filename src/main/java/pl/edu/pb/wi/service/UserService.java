package pl.edu.pb.wi.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.edu.pb.wi.entity.User;

public interface UserService extends UserDetailsService {
    User create(User user);
}
