package pl.edu.pb.wi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.exceptions.RegularException;
import pl.edu.pb.wi.repository.RoleRepository;
import pl.edu.pb.wi.repository.UserRepository;
import pl.edu.pb.wi.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws RegularException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new RegularException("User not found"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new RegularException("User not found"));
    }

    @Override
    public User createSimpleUser(User user) {
        user.setRole(roleRepository.getRoleByName(Role.RoleType.USER.getName()));
        return userRepository.save(user);
    }

    @Override
    public User createModerator(User user) {
        user.setRole(roleRepository.getRoleByName(Role.RoleType.MODERATOR.getName()));
        return userRepository.save(user);
    }

    @Override
    public User createAdmin(User user) {
        user.setRole(roleRepository.getRoleByName(Role.RoleType.ADMIN.getName()));
        return userRepository.save(user);
    }

    @Override
    public int deleteUserById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new RegularException("User not found"));
        userRepository.delete(user);
        return 1;
    }

    @Override
    public List<User> getAllModerators(){
        return userRepository.findUsersByRoleModerator();
    }
}
