package pl.edu.pb.wi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pb.wi.entity.Privilege;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.repository.PrivilegeRepository;
import pl.edu.pb.wi.repository.RoleRepository;
import pl.edu.pb.wi.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Component
public class DatabaseConfig {
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    PrivilegeRepository privilegeRepository;

    @Autowired
    public DatabaseConfig(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.privilegeRepository = privilegeRepository;
    }

    public void init() {
        /*
            Roles needed to access admin endpoints
            Simple users will not have any assigned roles
        */

        Collection<Privilege> adminPrivileges = new ArrayList<>();
        adminPrivileges.add(privilegeRepository.save(new Privilege("ADD_PRODUCT")));
        adminPrivileges.add(privilegeRepository.save(new Privilege("GET_PRODUCT")));
        adminPrivileges.add(privilegeRepository.save(new Privilege("DELETE_PRODUCT")));
        adminPrivileges.add(privilegeRepository.save(new Privilege("UPDATE_PRODUCT")));

        adminPrivileges.add(privilegeRepository.save(new Privilege("DELETE_USER")));
        adminPrivileges.add(privilegeRepository.save(new Privilege("BLOCK_USER")));

        adminPrivileges.add(privilegeRepository.save(new Privilege("ADD_SUPER_USER")));
        adminPrivileges.add(privilegeRepository.save(new Privilege("GET_SUPER_USER")));
        adminPrivileges.add(privilegeRepository.save(new Privilege("UPDATE_SUPER_USER")));
        adminPrivileges.add(privilegeRepository.save(new Privilege("DELETE_SUPER_USER")));

        roleRepository.save(Role.builder().privileges(adminPrivileges).name("ADMIN").build());

        Collection<Privilege> userPrivileges = new ArrayList<>();
        userPrivileges.add(privilegeRepository.getPrivilegeByName("ADD_PRODUCT"));
        userPrivileges.add(privilegeRepository.getPrivilegeByName("GET_PRODUCT"));
        userPrivileges.add(privilegeRepository.getPrivilegeByName("DELETE_PRODUCT"));
        userPrivileges.add(privilegeRepository.getPrivilegeByName("UPDATE_PRODUCT"));

        roleRepository.save(Role.builder().name("USER").privileges(userPrivileges).build());

        Collection<Privilege> moderatorPrivileges = new ArrayList<>();
        moderatorPrivileges.add(privilegeRepository.save(new Privilege("DELETE_USER")));
        moderatorPrivileges.add(privilegeRepository.save(new Privilege("BLOCK_USER")));

        roleRepository.save(Role.builder().name("MODERATOR").privileges(moderatorPrivileges).build());

        User adminUser = User.builder()
                .firstName("Admin")
                .lastName("Admin")
                .username("admin")
                .email("admin@store.com")
                .password(passwordEncoder.encode("admin"))
                .role(roleRepository.getRoleByName(Role.RoleType.ADMIN.getName()))
                .build();

        userRepository.save(adminUser);
    }
}
