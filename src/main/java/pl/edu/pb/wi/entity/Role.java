package pl.edu.pb.wi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "roles")
@RequiredArgsConstructor
@SuperBuilder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    /*
        -- Roles example --

        User        - simple application user
        Moderator   - can moderate application content (e.g. remove, block users / products)
                    - can't publish products
        Admin       - can do anything
     */
    public enum RoleType {
        USER("USER"),
        MODERATOR("MODERATOR"),
        ADMIN("ADMIN");

        final String name;

        RoleType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id")
    )
    private Collection<Privilege> privileges;
}
