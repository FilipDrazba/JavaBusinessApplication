package pl.edu.pb.wi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.exceptions.RegularException;
import pl.edu.pb.wi.repository.RoleRepository;
import pl.edu.pb.wi.repository.UserRepository;
import pl.edu.pb.wi.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_WhenUserDoesNotExist_ShouldThrowRegularException() {
        String username = "nonExistingUser";

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        RegularException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RegularException.class,
                () -> userService.loadUserByUsername(username)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testGetUserById_WhenUserDoesNotExist_ShouldThrowRegularException() {
        Long userId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RegularException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RegularException.class,
                () -> userService.getUserById(userId)
        );

        assertEquals("User not found", exception.getMessage());
    }
    @Test
    void createSimpleUser_ShouldReturnUserWithUserRole() {
        User user = new User();
        Role userRole = new Role();
        userRole.setName(Role.RoleType.USER.getName());

        when(roleRepository.getRoleByName(Role.RoleType.USER.getName())).thenReturn(userRole);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createSimpleUser(user);

        assertEquals(userRole, createdUser.getRole());
        verify(roleRepository, times(1)).getRoleByName(Role.RoleType.USER.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createModerator_ShouldReturnUserWithModeratorRole() {
        User user = new User();
        Role moderatorRole = new Role();
        moderatorRole.setName(Role.RoleType.MODERATOR.getName());

        when(roleRepository.getRoleByName(Role.RoleType.MODERATOR.getName())).thenReturn(moderatorRole);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createModerator(user);

        assertEquals(moderatorRole, createdUser.getRole());
        verify(roleRepository, times(1)).getRoleByName(Role.RoleType.MODERATOR.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createAdmin_ShouldReturnUserWithAdminRole() {
        User user = new User();
        Role adminRole = new Role();
        adminRole.setName(Role.RoleType.ADMIN.getName());

        when(roleRepository.getRoleByName(Role.RoleType.ADMIN.getName())).thenReturn(adminRole);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createAdmin(user);

        assertEquals(adminRole, createdUser.getRole());
        verify(roleRepository, times(1)).getRoleByName(Role.RoleType.ADMIN.getName());
        verify(userRepository, times(1)).save(user);
    }
    @Test
    void deleteUserById_ShouldDeleteUserAndReturnOne() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));

        int result = userService.deleteUserById(userId);

        assertEquals(1, result);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void getAllModerators_ShouldReturnListOfModerators() {
        List<User> moderators = new ArrayList<>();
        moderators.add(new User());
        moderators.add(new User());

        when(userRepository.findUsersByRoleModerator()).thenReturn(moderators);

        List<User> result = userService.getAllModerators();

        assertEquals(moderators, result);
        verify(userRepository, times(1)).findUsersByRoleModerator();
    }

    @Test
    void update_ShouldUpdateUserAndReturnUpdatedUser() {
        Long userId = 1L;
        User existingUser = new User();
        User updatedUser = new User();

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(userId, updatedUser);

        assertEquals(updatedUser, result);
        verify(userRepository, times(1)).findUserById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void getAllUsers_ShouldReturnListOfAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }
}
