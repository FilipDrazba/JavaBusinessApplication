package pl.edu.pb.wi;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.entity.constants.Role;
import pl.edu.pb.wi.repository.RoleRepository;
import pl.edu.pb.wi.repository.UserRepository;

import java.util.Collections;

@SpringBootApplication
@AllArgsConstructor
public class JavaBusinessApplication implements CommandLineRunner {
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(JavaBusinessApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(Role.builder().name("USER").build());
        roleRepository.save(Role.builder().name("MODERATOR").build());
        roleRepository.save(Role.builder().name("ADMIN").build());

        User adminUser = User.builder()
                .firstName("Admin")
                .lastName("Admin")
                .username("admin")
                .email("admin@store.com")
                .password(passwordEncoder.encode("admin"))
                .role(Collections.singletonList(
                    roleRepository.getRoleByName(Role.RoleType.ADMIN.getName())
                ))
                .build();

        userRepository.save(adminUser);
    }
}
