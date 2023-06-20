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
    BasketRepository basketRepository;
    ProductRepository productRepository;
    BasketProductRepository basketProductRepository;

    @Autowired
    public DatabaseConfig(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            BasketRepository basketRepository,
            ProductRepository productRepository,
            BasketProductRepository basketProductRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.basketProductRepository = basketProductRepository;
    }

    public void init() {
        /*
            Roles needed to access admin endpoints
            Simple users will not have any assigned roles
        */

        roleRepository.save(Role.builder().name("ADMIN").build());
        roleRepository.save(Role.builder().name("USER").build());
        roleRepository.save(Role.builder().name("MODERATOR").build());

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
