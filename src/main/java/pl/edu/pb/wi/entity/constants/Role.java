package pl.edu.pb.wi.entity.constants;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public enum RoleType {
        USER("USER"),
        ADMIN("ADMIN");

        String name;

        RoleType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
