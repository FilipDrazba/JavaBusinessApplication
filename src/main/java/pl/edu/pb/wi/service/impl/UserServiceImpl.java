package pl.edu.pb.wi.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

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
        logger.info("Loading user by username: {}", username);
        return userRepository.findUserByUsername(username).orElseThrow(() -> new RegularException("User not found"));
    }

    @Override
    public User getUserById(Long id) {
        logger.info("Getting user by ID: {}", id);
        return userRepository.findUserById(id).orElseThrow(() -> new RegularException("User not found"));
    }

    @Override
    public User createSimpleUser(User user) {
        logger.info("Creating simple user: {}", user);
        user.setRole(roleRepository.getRoleByName(Role.RoleType.USER.getName()));
        return userRepository.save(user);
    }

    @Override
    public User createModerator(User user) {
        logger.info("Creating moderator user: {}", user);
        user.setRole(roleRepository.getRoleByName(Role.RoleType.MODERATOR.getName()));
        return userRepository.save(user);
    }

    @Override
    public User createAdmin(User user) {
        logger.info("Creating admin user: {}", user);
        user.setRole(roleRepository.getRoleByName(Role.RoleType.ADMIN.getName()));
        return userRepository.save(user);
    }

    @Override
    public int deleteUserById(Long id) {
        logger.info("Deleting user by ID: {}", id);
        User user = userRepository.findUserById(id).orElseThrow(() -> new RegularException("User not found"));
        userRepository.delete(user);
        return 1;
    }

    @Override
    public List<User> getAllModerators() {
        return userRepository.findUsersByRoleModerator();
    }

    @Override
    public User update(Long id, User otherUser) {
        User thisUser = getUserById(id);
        thisUser.update(otherUser);
        return userRepository.save(thisUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
