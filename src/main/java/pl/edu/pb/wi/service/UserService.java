package pl.edu.pb.wi.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.edu.pb.wi.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserById(Long id);

    User createSimpleUser(User user);

    User createModerator(User user);

    User createAdmin(User user);

    int deleteUserById(Long id);

    List<User> getAllModerators();

    User update(Long id, User user);
}
