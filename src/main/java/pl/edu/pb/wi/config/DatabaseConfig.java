package pl.edu.pb.wi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pb.wi.entity.*;
import pl.edu.pb.wi.repository.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class DatabaseConfig {
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    PrivilegeRepository privilegeRepository;
    BasketRepository basketRepository;
    ProductRepository productRepository;
    BasketProductRepository basketProductRepository;

    @Autowired
    public DatabaseConfig(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PrivilegeRepository privilegeRepository,
            BasketRepository basketRepository,
            ProductRepository productRepository,
            BasketProductRepository basketProductRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.privilegeRepository = privilegeRepository;
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.basketProductRepository = basketProductRepository;
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

        User simpleUser = User.builder()
                .firstName("Jacek")
                .lastName("Kowalski")
                .username("jacek")
                .email("jacek@user.com")
                .password(passwordEncoder.encode("jacek"))
                .role(roleRepository.getRoleByName(Role.RoleType.USER.getName()))
                .build();

        Product product = new Product();
        product.setName("Something");
        product.setPrice(new BigDecimal("1.20"));

        productRepository.save(product);

        Basket basket = Basket.builder().user(simpleUser).products(List.of()).build();

        userRepository.save(adminUser);
        userRepository.save(simpleUser);

        basketRepository.save(basket);

        BasketProduct basketProduct = BasketProduct.builder().id(1L).product(product).quantity(3).build();
        basketProductRepository.save(basketProduct);
        basket.setProducts(List.of(basketProduct));

        basketRepository.save(basket);
    }
}
